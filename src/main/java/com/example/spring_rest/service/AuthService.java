package com.example.spring_rest.service;

import com.example.spring_rest.dto.RegisterRequest;
import com.example.spring_rest.dto.RegisterResponse;
import com.example.spring_rest.model.User; // Використовуй свою модель
import com.example.spring_rest.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest registerRequest) {
        // Перетворення RegisterRequest на User
        User user = modelMapper.map(registerRequest, User.class);

        // Кодування пароля
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Збереження користувача в базі даних
        User savedUser = userRepository.save(user);

        // Перетворення збереженого користувача на RegisterResponse
        return modelMapper.map(savedUser, RegisterResponse.class);
    }

    public boolean userByEmailExists(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    public boolean isValidPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}