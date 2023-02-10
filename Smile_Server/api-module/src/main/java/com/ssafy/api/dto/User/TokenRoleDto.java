package com.ssafy.api.dto.User;

import com.ssafy.core.code.Role;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * 로그인 후 리턴값으로 제공할 Dto
 *
 * author @서재건
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TokenRoleDto {

    @NotNull
    private String token;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    private Long userId;

    public TokenRoleDto of(String token, Role role, Long userId){
        return TokenRoleDto.builder()
                .token(token)
                .role(role)
                .userId(userId)
                .build();
    }
}
