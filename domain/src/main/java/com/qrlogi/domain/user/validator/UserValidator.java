package com.qrlogi.domain.user.validator;

import com.qrlogi.domain.user.dto.DeleteRequest;
import com.qrlogi.domain.user.entity.User;
import com.qrlogi.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
//
@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //삭제전검사
    public User validateDeleteUser(String username, DeleteRequest request) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }

        return user;

    }

    //권한추출
    public String getAuthority(UserDetails user) {

        return user.getAuthorities().stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Authority not found")).getAuthority();

    }

    //닉네임중복검사
    public void validateUserName(String username) {

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username in use");
        }

    }

    public User findUserByUsernameOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
