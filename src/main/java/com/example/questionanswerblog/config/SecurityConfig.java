package com.example.questionanswerblog.config;

import com.example.questionanswerblog.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.Security;

@Configuration // Marks this class as a configuration class where Spring beans are defined
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter){
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // PasswordEncoder bean for hashing passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http

                .cors(Customizer.withDefaults())
                // Disable CSRF for simplicity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth-> auth
                        //for preflight
                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()

                        // Public endpoints
                        .requestMatchers("/auth/**").permitAll()

                        // Allow ALL GET requests that does not require logged-in-user info without authentication
                        .requestMatchers(HttpMethod.GET,"/questions/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/answers/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/answers/question/{questionId}").permitAll()

                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                // Add the JWT filter **before** UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}


