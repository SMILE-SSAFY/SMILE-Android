package com.ssafy.core.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

/**
 *
 * author 김정은
 */
@Data
@Builder
public class ErrorResponseEntity {
    private int status;
    private String code;
    private String message;

    /**
     * ResponseEntity 로 변환
     *
     * @param e
     * @return
     */
    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(ErrorCode e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponseEntity.builder()
                        .status(e.getHttpStatus().value())
                        .code(e.name())
                        .message(e.getMessage())
                        .build());
    }
}
