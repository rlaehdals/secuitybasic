package com.example.securitybasic.domain;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

    ROLE_USER("유저"),
    ROLE_GUEST("게스트");

    private final String key;
}
