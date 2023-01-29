package com.ssafy.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTER_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 오류가 발생했습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    ALREADY_USE_PASSWORD(HttpStatus.BAD_REQUEST, "현재 사용 중인 비밀번호입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    HAS_EMAIL(HttpStatus.BAD_REQUEST, "존재하는 이메일입니다."),
    HAS_NICKNAME(HttpStatus.BAD_REQUEST, "존재하는 닉네임입니다."),
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."),
    IS_HEARTED(HttpStatus.BAD_REQUEST, "이미 좋아요했습니다."),
    HAS_RESERVATION(HttpStatus.NOT_FOUND, "예약이 존재합니다."),
    PHOTOGRAPHER_NOT_FOUND(HttpStatus.NOT_FOUND, "사진 작가를 찾을 수 없습니다."),
    SETTLEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "정산금이 존재하지 않습니다."),
    ALREADY_CANCELED(HttpStatus.NOT_FOUND, "이미 취소된 예약입니다."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "예약을 찾을 수 없습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."),
    FAIL_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "인증되지 않은 요청입니다."),
    FAIL_AUTHORIZATION(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다."),
    NO_IMAGE(HttpStatus.BAD_REQUEST, "이미지가 존재하지 않습니다."),
    HAS_PHONENUMBER(HttpStatus.BAD_REQUEST, "이미 등록된 휴대폰 번호입니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 카테고리는 존재하지 않습니다.")
    ;


    private final HttpStatus httpStatus;
    private final String message;
}
