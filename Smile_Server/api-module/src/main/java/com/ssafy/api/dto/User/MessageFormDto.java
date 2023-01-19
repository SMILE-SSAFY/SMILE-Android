package com.ssafy.api.dto.User;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import net.nurigo.sdk.message.model.Message;

@Builder
@Getter
@ToString
public class MessageFormDto {

    private Message message;
    private String randomNumber;
}
