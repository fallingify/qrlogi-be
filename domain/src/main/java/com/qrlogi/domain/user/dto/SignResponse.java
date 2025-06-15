package com.qrlogi.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignResponse {

    private Long id;
    private String username;
    private String email;
    private String role;

}
