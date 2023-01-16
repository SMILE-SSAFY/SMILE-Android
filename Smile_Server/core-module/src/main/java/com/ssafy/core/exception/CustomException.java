package com.ssafy.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * author 김정은
 */
@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    ErrorCode errorCode;
}
