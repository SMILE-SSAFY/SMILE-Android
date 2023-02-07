package com.ssafy.api.dto.User;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * 회원가입 Dto
 *
 * @author 서재건
 * @author 김정은
 */
@Data
@Builder
public class RegisterFormDto {
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String fcmToken;
}
