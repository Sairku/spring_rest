package com.example.spring_rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Вимкнути CSRF для спрощення (у реальному додатку це не рекомендується)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/accounts/**", "/customers/**", "/employers/**").authenticated() // Закрити доступ до всіх ендпоїнтів
                        .anyRequest().permitAll() // Дозволити доступ до інших ендпоїнтів (наприклад, для реєстрації)
                )
                .httpBasic(); // Використовувати базову автентифікацію

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Використовуємо BCrypt для кодування паролів
    }
}