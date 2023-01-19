package com.ssafy.api.service;

import com.ssafy.api.config.security.jwt.JwtTokenProvider;
import com.ssafy.api.dto.User.LoginUserDto;
import com.ssafy.api.dto.User.RegisterFormDto;
import com.ssafy.api.dto.User.TokenRoleDto;
import com.ssafy.core.code.Role;
import com.ssafy.core.entity.User;
import com.ssafy.core.exception.CustomException;
import com.ssafy.core.exception.ErrorCode;
import com.ssafy.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.core.exception.ErrorCode.INVALID_PASSWORD;
import static com.ssafy.core.exception.ErrorCode.USER_NOT_FOUND;

/**
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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
                .nickname(registerFormDto.getNickname())
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
}
