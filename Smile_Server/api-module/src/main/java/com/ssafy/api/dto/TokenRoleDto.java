package com.ssafy.api.dto;

import com.ssafy.core.code.Role;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * 로그인 후 리턴값으로 제공할 Dto
 *
 * author @서재건
 */
@Builder
@Getter
@Setter
@ToString
public class TokenRoleDto {

    @NotNull
    private String token;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;
}
