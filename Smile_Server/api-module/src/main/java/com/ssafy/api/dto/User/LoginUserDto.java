package com.ssafy.api.dto.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 로그인 Dto
 *
 * @author 서재건
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String fcmToken;

    public LoginUserDto of(RegisterFormDto user){
        return LoginUserDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .fcmToken(user.getFcmToken())
                .build();
    }
}
