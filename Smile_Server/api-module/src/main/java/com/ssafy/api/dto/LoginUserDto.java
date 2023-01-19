package com.ssafy.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * 로그인 Dto
 *
 * author @서재건
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
}
