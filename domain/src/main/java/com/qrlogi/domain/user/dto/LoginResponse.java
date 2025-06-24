package com.qrlogi.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }
}