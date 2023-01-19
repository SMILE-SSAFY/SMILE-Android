package com.ssafy.api.controller;

import com.ssafy.api.config.security.jwt.JwtTokenProvider;
import com.ssafy.api.dto.LoginUserDto;
import com.ssafy.api.dto.RegisterFormDto;
import com.ssafy.api.dto.TokenRoleDto;
import com.ssafy.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Base64;

/**
 *  유저 관련 Controller
 *
 * author @서재건
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 토큰으로부터 userId를 추출
     *
     * @param request
     * @return userId 값 리턴
     */
    @GetMapping("/userId")
    public Long getUserId(HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        log.info(token);
        log.info(jwtTokenProvider.getUserIdx(token));
        String userId = jwtTokenProvider.getUserIdx(token);

        return Long.valueOf(userId);
    }

    /**
     * 회원가입 후 로그인 진행
     *
     * @param registerFormDto // email, password, name, nickname, phoneNumber
     * @return token    // login을 통한 jwt token 리턴
     */
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenRoleDto> register(@RequestBody @Valid RegisterFormDto registerFormDto) {
        TokenRoleDto tokenRoleDto = userService.registerUser(registerFormDto);
        return ResponseEntity.ok().body(tokenRoleDto);
    }

    /**
     * 로그인
     *
     * @param loginUserDto // email, password
     * @return token    // jwt token 리턴
     */
    @GetMapping(value = "/login")
    public ResponseEntity<TokenRoleDto> login(@RequestBody LoginUserDto loginUserDto) {
        TokenRoleDto tokenRoleDto = userService.login(loginUserDto);
        return ResponseEntity.ok().body(tokenRoleDto);
    }
}
