package com.example.spring_rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/accounts/**", "/customers/**", "/employers/**").authenticated()  // Захистити ендпоїнти
                        .anyRequest().permitAll()  // Інші ендпоїнти доступні без авторизації
                )
                .formLogin()  // Стандартна форма авторизації
                .permitAll()
                .and()
                .httpBasic();  // Використовуємо базову автентифікацію

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Шифруємо паролі
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            if (username.equals("admin")) {
                return User.withUsername("admin")
                        .password(passwordEncoder().encode("admin123")) // Логін: admin, пароль: admin123
                        .roles("ADMIN")
                        .build();
            } else if (username.equals("user")) {
                return User.withUsername("user")
                        .password(passwordEncoder().encode("user123")) // Логін: user, пароль: user123
                        .roles("USER")
                        .build();
            }
            throw new RuntimeException("User not found");
        };
    }
}
