package com.qrlogi.domain.user.service;


import com.qrlogi.domain.user.entity.User;
import com.qrlogi.domain.user.entity.UserPrincipal;
import com.qrlogi.domain.user.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserDetailService implements UserDetailsService {

    private final UserValidator userValidator;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User validatedUser = userValidator.findUserByUsernameOrThrow(username);

        return new UserPrincipal(validatedUser);
    }
}
