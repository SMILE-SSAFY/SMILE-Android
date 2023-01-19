package com.ssafy.core.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Role enum
 *
 * author @서재건
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    USER("USER"),
    PHOTOGRAPHER("PHOTOGRAPHER")
    ;

    private final String name;

}
