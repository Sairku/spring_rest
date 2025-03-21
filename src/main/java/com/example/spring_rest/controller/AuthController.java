package com.example.spring_rest.controller;

import com.example.spring_rest.dto.LoginResponse;
import com.example.spring_rest.dto.RegisterRequest;
import com.example.spring_rest.dto.RegisterResponse;
import com.example.spring_rest.service.AuthService;
import com.example.spring_rest.service.CustomUserDetailsService;
import com.example.spring_rest.util.JwtUtil;
import com.example.spring_rest.util.ResponseHandler;
import com.example.spring_rest.validation.FullUpdate;
import com.example.spring_rest.validation.PartialUpdate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Validated(FullUpdate.class) RegisterRequest registerRequest) {
        log.info("Registering new user with email: {}", registerRequest.getEmail());
        if (authService.userByEmailExists(registerRequest.getEmail())) {
            log.warn("User with email {} already exists", registerRequest.getEmail());
            return ResponseHandler.generateResponse(
                    HttpStatus.BAD_REQUEST,
                    true,
                    "User already exists",
                    null
            );
        }

        RegisterResponse registerResponse = authService.register(registerRequest);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerRequest.getUsername(), registerRequest.getPassword())
        );
        String jwtToken = jwtUtil.generateToken(registerResponse.getUsername());

        registerResponse.setToken(jwtToken);

        log.info("User registered successfully: {}", registerResponse.getEmail());
        return ResponseHandler.generateResponse(
                HttpStatus.CREATED,
                false,
                "User registered successfully",
                registerResponse
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Validated(PartialUpdate.class) RegisterRequest registerRequest) {
        log.info("Login attempt for user: {}", registerRequest.getEmail() != null ? registerRequest.getEmail() : registerRequest.getUsername());
        if (registerRequest.getEmail() == null && registerRequest.getUsername() == null) {
            log.warn("Login failed: Email or username is required");
            return ResponseHandler.generateResponse(
                    HttpStatus.BAD_REQUEST,
                    true,
                    "Email or username is required",
                    null
            );
        }

        UserDetails userDetails;
        if (registerRequest.getEmail() != null) {
            // Завантажуємо користувача за електронною поштою
            userDetails = userDetailsService.loadUserByEmail(registerRequest.getEmail());
        } else {
            // Завантажуємо користувача за іменем користувача
            userDetails = userDetailsService.loadUserByUsername(registerRequest.getUsername());
        }

        // Перевірка пароля
        if (!authService.isValidPassword(registerRequest.getPassword(), userDetails.getPassword())) {
            log.warn("Login failed: Invalid password for user {}", userDetails.getUsername());
            return ResponseHandler.generateResponse(
                    HttpStatus.BAD_REQUEST,
                    true,
                    "Invalid password",
                    null
            );
        }

        // Аутентифікація
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), registerRequest.getPassword())
        );

        // Генерація JWT токена
        String jwtToken = jwtUtil.generateToken(userDetails.getUsername());

        log.info("User logged in successfully: {}", userDetails.getUsername());
        return ResponseHandler.generateResponse(
                HttpStatus.OK,
                false,
                "User logged in successfully",
                new LoginResponse(jwtToken)
        );
    }

}
