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
     * header로 오는 token에서 정보 추출
     *
     * @param request
     * @return header : {"alg":"HS256"}
     * payload : {"sub":"1","role":"USER","index":"1","iat":1673706867,"exp":1676298867}
     */
    @GetMapping(value = "/token")
    public String checkToken(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);

        String[] token_list = token.split("\\.");
        log.info("token_list size : {}", token_list.length);

        Base64.Decoder decoder = Base64.getUrlDecoder();
        String header = new String(decoder.decode(token_list[0]));
        String payload = new String(decoder.decode(token_list[1]));

        return "header : " + header + "\n" + "payload : " + payload;
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
