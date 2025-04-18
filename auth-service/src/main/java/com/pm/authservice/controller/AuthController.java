package com.pm.authservice.controller;

import com.pm.authservice.dto.LoginRequestDto;
import com.pm.authservice.dto.LoginResponseDto;
import com.pm.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

            @Operation(summary = "Generate token on user login")
            @PostMapping("/login")

        public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {

            Optional<String> tokenOptional = authService.authenticate(loginRequestDto);
            if (tokenOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String token=tokenOptional.get();
            return ResponseEntity.ok(new LoginResponseDto(token));
        }
        }



