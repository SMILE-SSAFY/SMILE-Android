package com.ssafy.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.config.security.jwt.JwtTokenProvider;
import com.ssafy.api.dto.Kakao.KakaoProfileDto;
import com.ssafy.api.dto.User.LoginUserDto;
import com.ssafy.api.dto.User.MessageFormDto;
import com.ssafy.api.dto.User.RegisterFormDto;
import com.ssafy.api.dto.User.TokenRoleDto;
import com.ssafy.api.dto.User.UserDto;
import com.ssafy.core.code.Role;
import com.ssafy.core.entity.Article;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.User;
import com.ssafy.core.exception.CustomException;
import com.ssafy.core.exception.ErrorCode;
import com.ssafy.core.repository.UserRepository;
import com.ssafy.core.repository.article.ArticleRepository;
import com.ssafy.core.repository.photographer.PhotographerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


/**
 * 유저 관련 기능 클래스
 *
 * @author 서재건
 * @author 신민철
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ArticleRepository articleRepository;
    private final S3UploaderService s3UploaderService;
    private final PhotographerRepository photographerRepository;

    @Value("${kakao.oauth2.secretPassword}")
    String kakaoPassword;

    /***
     * 로그인한 유저를 얻어오는 함수
     * @return user
     */
    public static User getLogInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }


    /**
     * 회원가입 및 로그인
     *
     * @param registerFormDto
     * @return 회원가입 후 로그인을 통해 발급받은 jwt 토큰dto을 반환한다.
     * @throws HAS_PHONENUMBER
     */
    public TokenRoleDto registerUser(RegisterFormDto registerFormDto) {

        log.info("[registerUser] RegisterFormDto 객체 : {}", registerFormDto.toString());

        // 카카오 전용 휴대폰 번호(11112345678)으로 회원가입 신청이 아니면 휴대폰 번호 중복 체크
        if (!registerFormDto.getPhoneNumber().equals("11112345678") && userRepository.existsByPhoneNumber(registerFormDto.getPhoneNumber())) {
            throw new CustomException(ErrorCode.HAS_PHONENUMBER);
        }

        // 이메일이 존재하면 등록 제외하고 로그인 진행
        if (!userRepository.existsByEmail(registerFormDto.getEmail())) {
            User user = User.builder()
                    .email(registerFormDto.getEmail())
                    .password(passwordEncoder.encode(registerFormDto.getPassword()))
                    .name(registerFormDto.getName())
                    .phoneNumber(registerFormDto.getPhoneNumber())
                    .role(Role.USER)
                    .fcmToken(registerFormDto.getFcmToken() + ",")
                    .build();

            userRepository.save(user);
            log.info("[registerUser] 회원등록 완료");
        }

        LoginUserDto loginUserDto = LoginUserDto.builder()
                .email(registerFormDto.getEmail())
                .password(registerFormDto.getPassword())
                .fcmToken(registerFormDto.getFcmToken())
                .build();

        return login(loginUserDto);
    }

    /**
     * 로그인을 통해 jwt 토큰을 발급한다.
     *
     * @param loginUserDto
     * @return jwt tokenDto
     */
    public TokenRoleDto login(LoginUserDto loginUserDto) {
        log.info("user 로그인 진행");
        User user;
        user = userRepository.findByEmail(loginUserDto.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        log.info("user 객체 반환");

        if (!passwordEncoder.matches(loginUserDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        log.info("유저 존재 및 비밀번호 일치");

        if (user.getFcmToken() == null || !user.getFcmToken().contains(loginUserDto.getFcmToken())) {
            user.updateFcmToken(user.getFcmToken() + loginUserDto.getFcmToken() + ",");
            user = userRepository.save(user);
            log.info("fcmToken 추가 : {}", user.getFcmToken());
        }

        String token = jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRole().getName());
        log.info("jwt token 생성");

        return TokenRoleDto.builder()
                .token(token)
                .role(user.getRole())
                .userId(user.getId())
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
        StringBuilder randomNumber = new StringBuilder();

        for (int i = 0; i < letter; i++) {
            createNum = random.nextInt(9);
            ranNum = Integer.toString(createNum);
            randomNumber.append(ranNum);
        }
        return randomNumber.toString();
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
     * @param fcmToken
     * @return 카카오로부터 받은 정보로 회원 가입 후 로그인 진행하여 jwt 토큰 리턴
     */
    public TokenRoleDto kakaoLogin(String accessToken, String fcmToken) {
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
                .phoneNumber("11112345678")
                .fcmToken(fcmToken)
                .build();

        return registerUser(registerFormDto);
    }

    /**
     * 모바일에서 넘겨준  access 토큰을 통해 회원 정보가 담긴 response를 받는다.
     *
     * @param accessToken
     * @return 요청한 회원 정보가 담긴 response 반환
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
        return rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );
    }

    /**
     * 회원정보가 담긴 response를 객체화하여 kakaoProfileDto에 담는다.
     *
     * @param response // 회원 정보가 담긴 response
     * @return 회원정보가 담긴 dto 반환
     */
    public KakaoProfileDto kakaoProfile(ResponseEntity<String> response) {
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfileDto kakaoProfileDto = null;
        try {
            kakaoProfileDto = objectMapper.readValue(response.getBody(), KakaoProfileDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return kakaoProfileDto;
    }


    /**
     * token에서 유저 정보 조회 후 회원 탈퇴
     *
     * @param request
     */
    @Transactional
    public void removeUser() {
        Long userId = getLogInUser().getId();
        log.info("token에 저장된 userId : {}", userId);

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        log.info("DB에 저장된 userId : {}", user.getId());

        if (user.getRole().equals(Role.PHOTOGRAPHER)) {
            log.info("유저가 사진작가일 경우");
            Photographer photographer = photographerRepository.findById(userId)
                    .orElseThrow(() -> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));

            // 이미지 삭제
            if (photographer.getProfileImg() != null) {
                s3UploaderService.deleteFile(photographer.getProfileImg().trim());
            }
            photographerRepository.delete(photographer);
        }

        log.info("유저의 게시글 조회");
        List<Article> articleList = articleRepository.findByUserIdOrderByIdDesc(user.getId());
        for (Article article : articleList) {
            String photoUriList = article.getPhotoUrls().replace("[", "").replace("]", "");
            List<String> photoUrls = new ArrayList<>(Arrays.asList(photoUriList.split(",")));
            photoUrls.forEach(str -> s3UploaderService.deleteFile(str.trim()));
            articleRepository.delete(article);
        }

        userRepository.deleteById(userId);
    }

    /**
     * jwt 토큰을 통한 user 정보 조회
     *
     * @return user
     */
    public UserDto getUser() {
        User user = getLogInUser();
        String photoUrl = null;
        if (user.getRole().equals(Role.PHOTOGRAPHER)) {
            photoUrl = photographerRepository.findById(user.getId())
                    .orElseThrow(() -> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND))
                    .getProfileImg();
        }
        return new UserDto().of(user, photoUrl);
    }

    /**
     * 로그아웃하며 fcmToken 삭제
     *
     * @param fcmToken
     */
    public void logout(String fcmToken) {
        User user = getLogInUser();

        log.info("삭제할 fcmToken : {}", fcmToken);
        log.info("user fcmToken : {}", user.getFcmToken());

        String deletedFcmToken = user.getFcmToken().replace(fcmToken + ",", "");

        log.info("삭제 후 fcmToken : {}", deletedFcmToken);

        user.updateFcmToken(deletedFcmToken);
        userRepository.save(user);
    }
}
