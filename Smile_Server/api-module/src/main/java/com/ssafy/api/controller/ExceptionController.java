package com.ssafy.api.controller;

import com.ssafy.core.exception.CustomException;
import com.ssafy.core.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping("/entrypoint")
    public void entrypointException() {
        throw new CustomException(ErrorCode.FAIL_AUTHENTICATION);
    }

    @GetMapping("/autorization")
    public void deniedHandler() {
        throw new CustomException(ErrorCode.FAIL_AUTHENTICATION);
    }
}
