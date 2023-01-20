package com.ssafy.api.controller;

import com.ssafy.api.dto.User.TokenRoleDto;
import com.ssafy.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 카카오 로그인 컨트롤러
 *
 * author @서재건
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class KakaoController {

    private final UserService userService;

    /**
     * 카카오 인가코드를 포함한 uri를 잡아서 카카오 로그인 및 회원가입 실행
     *
     * @param code  //인가코드
     * @return TokenRoleDto // token, role
     */
    @GetMapping("/auth/kakao/callback")
    public ResponseEntity<TokenRoleDto> kakaoLogin(String code) {
        TokenRoleDto tokenDto = userService.kakaoLogin(code);
        return ResponseEntity.ok().body(tokenDto);
    }
}
