package com.ssafy.api.dto.User;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import net.nurigo.sdk.message.model.Message;

/**
 * 휴대폰 인증 번호 DTO
 *
 * @author 서재건
 */
@Builder
@Getter
@ToString
public class MessageFormDto {

    private Message message;
    private String randomNumber;
}
