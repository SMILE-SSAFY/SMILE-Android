package com.ssafy.api.controller;

import com.ssafy.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * 테스트 로그인
     *
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<?> login(){
        return ResponseEntity.ok(userService.getUser());
    }
}
