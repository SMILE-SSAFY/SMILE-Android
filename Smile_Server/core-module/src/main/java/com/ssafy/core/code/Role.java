package com.ssafy.core.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER"),
    PHOTOGRAPHER("ROLE_PHOTOGRAPHER")
    ;

    private final String name;

}
