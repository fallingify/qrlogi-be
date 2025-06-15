package com.qrlogi.domain.user.service;


import com.qrlogi.domain.user.dto.DeleteRequest;
import com.qrlogi.domain.user.dto.SignRequest;
import com.qrlogi.domain.user.dto.SignResponse;
import com.qrlogi.domain.user.entity.User;
import com.qrlogi.domain.user.entity.UserRole;
import com.qrlogi.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    /*
    회원탙퇴용도의 getUser()
     */
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public void deleteCurrentUser(DeleteRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User goneUser = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(), goneUser.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        userRepository.delete(goneUser);

    }
}
