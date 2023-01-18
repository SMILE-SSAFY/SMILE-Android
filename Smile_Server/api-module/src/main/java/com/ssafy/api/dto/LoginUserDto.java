package com.ssafy.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

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
