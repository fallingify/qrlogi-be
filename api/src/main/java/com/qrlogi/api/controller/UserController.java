package com.qrlogi.api.controller;


import com.qrlogi.user.dto.SignRequest;
import com.qrlogi.user.dto.SignResponse;
import com.qrlogi.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final AuthService authService;

    @PostMapping("/signup")
    public SignResponse signup(@RequestBody SignRequest signRequest) {
        return authService.signUp(signRequest);

    }


}
