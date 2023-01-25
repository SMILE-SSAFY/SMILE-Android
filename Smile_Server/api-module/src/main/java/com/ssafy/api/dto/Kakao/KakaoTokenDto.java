package com.ssafy.api.dto.Kakao;

import lombok.Getter;

/**
 * 모바일에서 넘겨주는 kakao access token
 *
 * author @서재건
 */
@Getter
public class KakaoTokenDto {
    private String token;
}