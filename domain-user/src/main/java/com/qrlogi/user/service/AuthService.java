package com.qrlogi.user.service;

import com.qrlogi.user.dto.SignRequest;
import com.qrlogi.user.dto.SignResponse;
import com.qrlogi.user.entity.User;
import com.qrlogi.user.entity.UserRole;
import com.qrlogi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public SignResponse signUp(SignRequest signRequest) {
        if(userRepository.findByUsername(signRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already in use");
        }

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

}
