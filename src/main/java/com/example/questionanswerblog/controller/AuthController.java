package com.example.questionanswerblog.controller;

import com.example.questionanswerblog.dto.response.AuthResponseDTO;
import com.example.questionanswerblog.model.User;
import com.example.questionanswerblog.dto.response.UserDTO;
import com.example.questionanswerblog.dto.request.RegisterRequestDTO;
import com.example.questionanswerblog.dto.request.LoginRequestDTO;
import com.example.questionanswerblog.security.JwtUtil;
import com.example.questionanswerblog.service.AuthService;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

@RestController // Marks this class as a REST controller that returns JSON responses
@RequestMapping("/auth") // Base URL path for authentication endpoints
public class AuthController {

    private final AuthService authService; // Service layer used to handle authentication logic
    private final JwtUtil jwtUtil; // Utility class used to generate and validate JWT tokens

    // Constructor injection used by Spring to provide dependencies
    public AuthController(AuthService authService, JwtUtil jwtUtil){
        this.authService = authService; // Spring injects AuthService when creating this controller
        this.jwtUtil = jwtUtil;
    }

    // Endpoint to register a new user (POST /auth/register)
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDTO request){// @RequestBody converts incoming JSON into RegisterRequest object
        authService.register(request);
        return "User registered successfully!";

    }

    /// Endpoint to login an existing user (POST /auth/login)
    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO request){

        // Authenticate user using service layer
        User user = authService.login(request);

        // Convert entity -> DTO (avoid sending password to client)
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(),
                                        user.getEmail(), user.getRole());

        // Generate JWT token using user's email
        String jwtToken = jwtUtil.generateToken(user.getEmail());

        // Populate response object
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(jwtToken, userDTO);

        // Return token + user info to client
        return authResponseDTO;

    }
}
