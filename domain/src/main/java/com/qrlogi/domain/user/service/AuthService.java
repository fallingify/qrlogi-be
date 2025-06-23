package com.qrlogi.domain.user.service;


import com.qrlogi.domain.jwt.*;
import com.qrlogi.domain.user.dto.*;
import com.qrlogi.domain.user.entity.User;
import com.qrlogi.domain.user.entity.UserRole;
import com.qrlogi.domain.user.repository.UserRepository;
import com.qrlogi.domain.user.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserValidator userValidator;


    public SignResponse signUp(SignRequest signRequest) {

        userValidator.validateUserName(signRequest.getUsername());

        User user = User.builder()
                .username(signRequest.getUsername())
                .password(passwordEncoder.encode(signRequest.getPassword()))
                .email(signRequest.getEmail())
                .role(UserRole.WORKER)
                .build();

        User signedUser = userRepository.save(user);

        return new SignResponse(
                signedUser.getId(),
                signedUser.getUsername(),
                signedUser.getEmail(),
                signedUser.getRole().name()
        );
    }

    public LoginResponse login(LoginRequest loginRequest) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        UserDetails user = (UserDetails) auth.getPrincipal();
        String ValidatedAuthority =  userValidator.getAuthority(user);
        String token = jwtTokenProvider.createToken(user.getUsername(), ValidatedAuthority);

        return new LoginResponse(token);
    }




    public void deleteCurrentUser(DeleteRequest request) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User ValidatedUser = userValidator.validateDeleteUser(username, request);

        userRepository.delete(ValidatedUser);

    }
}
