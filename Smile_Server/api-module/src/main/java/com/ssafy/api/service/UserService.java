package com.ssafy.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.config.security.jwt.JwtTokenProvider;
import com.ssafy.api.dto.Kakao.KakaoProfileDto;
import com.ssafy.api.dto.User.LoginUserDto;
import com.ssafy.api.dto.User.MessageFormDto;
import com.ssafy.api.dto.User.RegisterFormDto;
import com.ssafy.api.dto.User.TokenRoleDto;
import com.ssafy.core.code.Role;
import com.ssafy.core.entity.User;
import com.ssafy.core.exception.CustomException;
import com.ssafy.core.exception.ErrorCode;
import com.ssafy.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

import static com.ssafy.core.exception.ErrorCode.INVALID_PASSWORD;
import static com.ssafy.core.exception.ErrorCode.USER_NOT_FOUND;

/**
 * 유저 관련 기능 클래스
 *
 * author @서재건
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${kakao.oauth2.secretPassword}")
    String kakaoPassword;

    /**
     * 회원가입 및 로그인
     * @param registerFormDto
     * @return
     * 회원가입 후 로그인을 통해 발급받은 jwt 토큰dto을 반환한다.
     */
    public TokenRoleDto registerUser(RegisterFormDto registerFormDto) {

        log.info("[registerUser] RegisterFormDto 객체 : {}", registerFormDto.toString());

        // 이메일이 존재할 때 에러 발생
        if (userRepository.findByEmail(registerFormDto.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.IS_REGISTERED);
        }

        User user = User.builder()
                .email(registerFormDto.getEmail())
                .password(passwordEncoder.encode(registerFormDto.getPassword()))
                .name(registerFormDto.getName())
                .phoneNumber(registerFormDto.getPhoneNumber())
                .role(Role.USER)
                .build();
        log.info("[registerUser] User 객체 : {}", user.toString());

        User savedUser = userRepository.save(user);
        log.info("[registerUser] 회원등록 완료");


        LoginUserDto loginUserDto = LoginUserDto.builder()
                .email(registerFormDto.getEmail())
                .password(registerFormDto.getPassword())
                .build();

        return login(loginUserDto);
    }

    /**
     * 로그인을 통해 jwt 토큰을 발급한다.
     *
     * @param loginUserDto
     * @return
     * jwt tokenDto
     */
    public TokenRoleDto login(LoginUserDto loginUserDto) {
        User user = userRepository.findByEmail(loginUserDto.getEmail()).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        log.info("user 객체 반환");

        if (!passwordEncoder.matches(loginUserDto.getPassword(), user.getPassword())) {
            throw new CustomException(INVALID_PASSWORD);
        }
        log.info("유저 존재 및 비밀번호 일치");

        String token = jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRole().getName());
        log.info("jwt token 생성");

        return TokenRoleDto.builder()
                .token(token)
                .role(user.getRole())
                .build();
    }

    /**
     * 이메일이 중복이면 에러를 던진다.
     *
     * @param email
     * @throws HAS_EMAIL 이메일이 존재할 때 에러 발생
     */
    public void checkEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.HAS_EMAIL);
        }
    }

    /**
     * 4자리 난수 생성
     *
     * @return randomNumber //난수 4자리
     */
    public String createRandomNumber() {
        Random random = new Random();
        int createNum = 0;
        String ranNum = "";
        int letter = 4;
        String randomNumber = "";

        for (int i = 0; i < letter; i++) {
            createNum = random.nextInt(9);
            ranNum = Integer.toString(createNum);
            randomNumber += ranNum;
        }
        return randomNumber;
    }

    /**
     * 메세지 폼 생성하고 난수와 메세지 폼 반환
     *
     * @param fromNumber
     * @param phoneNumber
     * @return MessageFormDto
     * Message message, String randomNumber
     */
    public MessageFormDto createMessageForm(String fromNumber, String phoneNumber) {
        String randomNumber = createRandomNumber();

        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom(fromNumber);
        message.setTo(phoneNumber);
        message.setText("인증번호 [" + randomNumber + "]를 입력하세요.");

        return MessageFormDto.builder()
                .message(message)
                .randomNumber(randomNumber)
                .build();
    }

    /**
     * 카카오 로그인을 통해 회원가입 하고 로그인하여 jwt 토큰을 발행한다.
     *
     * @param accessToken
     * @return
     * 카카오로부터 받은 정보로 회원 가입 후 로그인 진행하여 jwt 토큰 리턴
     */
    public TokenRoleDto kakaoLogin(String accessToken) {
        log.info("accessToken : {}", accessToken);
        ResponseEntity<String> profileResponse = kakaoProfileResponse(accessToken);
        log.info("카카오 정보 profileResponse : {}", profileResponse.getBody().toString());

        KakaoProfileDto kakaoProfileDto = kakaoProfile(profileResponse);
        log.info("카카오 아이디(번호) : {}", kakaoProfileDto.getId());
        log.info("카카오 닉네임 : {}", kakaoProfileDto.getProperties().getNickname());
        log.info("카카오 이메일 : {}", kakaoProfileDto.getKakao_account().getEmail());

        RegisterFormDto registerFormDto = RegisterFormDto.builder()
                .email(kakaoProfileDto.getKakao_account().getEmail())
                .password(kakaoPassword)
                .name(kakaoProfileDto.getProperties().getNickname())
                .phoneNumber("01012345678")
                .build();

        return registerUser(registerFormDto);

    }

    /**
     * 모바일에서 넘겨준  access 토큰을 통해 회원 정보가 담긴 response를 받는다.
     *
     * @param accessToken
     * @return
     * 요청한 회원 정보가 담긴 response 반환
     */
    public ResponseEntity<String> kakaoProfileResponse(String accessToken) {
        // POST방식으로 key=value 데이터를 요청(카카오쪽으로)
        RestTemplate rt = new RestTemplate();
        rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        // Http 요청하기 - Post 방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );
        return response;
    }

    /**
     * 회원정보가 담긴 response를 객체화하여 kakaoProfileDto에 담는다.
     *
     * @param response     // 회원 정보가 담긴 response
     * @return
     * 회원정보가 담긴 dto 반환
     */
    public KakaoProfileDto kakaoProfile(ResponseEntity<String> response) {
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfileDto kakaoProfileDto = null;
        try {
            kakaoProfileDto = objectMapper2.readValue(response.getBody(), KakaoProfileDto.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return kakaoProfileDto;
    }

}
