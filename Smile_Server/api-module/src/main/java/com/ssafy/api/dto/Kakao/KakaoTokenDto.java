package com.ssafy.api.dto.Kakao;

import lombok.Data;

/**
 * 카카오에서 제공하는 토큰 Dto
 *
 * author @서재건
 */
@Data
public class KakaoTokenDto {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;
}