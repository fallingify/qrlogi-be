package com.qrlogi.api.controller;


import com.qrlogi.api.config.JwtTokenProvider;
import com.qrlogi.domain.user.dto.*;
import com.qrlogi.domain.user.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public SignResponse signup(@RequestBody SignRequest signRequest) {
        return authService.signUp(signRequest);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        UserDetails user = (UserDetails) auth.getPrincipal();
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getAuthorities().stream().findFirst().get().getAuthority());


        return ResponseEntity.ok(new LoginResponse(token));
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
