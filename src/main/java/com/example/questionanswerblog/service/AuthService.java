package com.example.questionanswerblog.service;

import com.example.questionanswerblog.model.User;
import com.example.questionanswerblog.repository.UserRepository;
import com.example.questionanswerblog.dto.request.LoginRequestDTO;
import com.example.questionanswerblog.dto.request.RegisterRequestDTO;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //Constructor injection for repository and password encoder
    public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // findByUsername() returns an Optional<User>
    public User findByUserName(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username not found!"));
    }

    // findByEmail() returns an Optional<User>
    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User email not found!"));
    }

    public User getCurrentUser(){

        // Get the username of the currently authenticated user
        // SecurityContextHolder stores security information for the current request/thread
        String email = SecurityContextHolder
                .getContext() // retrieves the SecurityContext for the current thread
                .getAuthentication() // gets the Authentication object representing the logged-in user
                .getName(); // returns the username (principal name)

        // Look up the User entity in the database using the username
        return userRepository.findByEmail(email)
                // If the user is not found in the database, throw an exception
                .orElseThrow(() -> new RuntimeException("User not found!"));

    }

    public void register(RegisterRequestDTO request){
        //Check if username already exists
        if (userRepository.existsByUsername(request.getUsername()))
                throw new RuntimeException("Username already exists!") ;

        //Check if email already exists
        if (userRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException ("Email already exists!") ;

        //Create new user object
        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        //Encode password before saving
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        //Set default role
        user.setRole("USER");

        //Save user in database
        userRepository.save(user);
    }

    public User login(LoginRequestDTO request) {

        //Find user by username
        User user = findByUserName(request.getUsername());

        //Check if password matches
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Password mismatch!");
        }

        return user;
    }
}
