package com.ssafy.core.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("USER"),
    PHOTOGRAPHER("PHOTOGRAPHER")
    ;

    private final String name;

}
