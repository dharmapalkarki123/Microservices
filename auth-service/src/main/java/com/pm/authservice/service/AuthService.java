package com.pm.authservice.service;

import com.pm.authservice.dto.LoginRequestDto;
import com.pm.authservice.model.User;
import com.pm.authservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public Optional<String> authenticate(LoginRequestDto loginRequestDto){
        Optional<User> user=userService.
        findByEmail(loginRequestDto.getEmail())
                .filter(u->passwordEncoder.matches(loginRequestDto.getPassword(),
                        u.getPassword()))
                .mao(u->jwtUtil.generateToken(u.getEmail(),u.getRole()));

        return token;
    }

}
