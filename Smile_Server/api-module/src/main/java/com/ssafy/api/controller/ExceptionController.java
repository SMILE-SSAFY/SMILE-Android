package com.ssafy.api.controller;

import com.ssafy.core.exception.CustomException;
import com.ssafy.core.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * security에서 authentication, authorization 에러 전달받아 throw하는 Controller
 *
 * @author 서재건
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/exception")
public class ExceptionController {

    /**
     * CustomAccessDeniedHandler에서 redirect로 오면 authorization error throw
     */
    @GetMapping("/entrypoint")
    public void entrypointException() {
        throw new CustomException(ErrorCode.FAIL_AUTHENTICATION);
    }

    /**
     * CustomAuthenticationEntryPoint에서 redirect로 오면 authentication error throw
     */
    @GetMapping("/autorization")
    public void deniedHandler() {
        throw new CustomException(ErrorCode.FAIL_AUTHENTICATION);
    }
}
