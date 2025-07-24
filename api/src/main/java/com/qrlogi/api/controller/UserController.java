package com.qrlogi.api.controller;


import com.qrlogi.domain.user.dto.*;
import com.qrlogi.domain.user.entity.UserPrincipal;
import com.qrlogi.domain.user.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final AuthService authService;

    @PostMapping("/signup")
    public SignResponse signup(@RequestBody SignRequest signRequest) {
        return authService.signUp(signRequest);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
       return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest r) {
        return ResponseEntity.ok("로그아웃되었습니다.");
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody DeleteRequest request) {
        authService.deleteCurrentUser(request);
        return ResponseEntity.ok("탈퇴되었습니다.");
    }


}
