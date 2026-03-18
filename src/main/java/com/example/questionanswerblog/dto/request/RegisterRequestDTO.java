package com.example.questionanswerblog.dto.request;

// DTO used to receive user registration data from the client
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String email;

    public RegisterRequestDTO(){} // No-argument constructor required for request deserialization

    // Constructor used to create a RegisterRequest object with values
    public RegisterRequestDTO(String username, String password, String email){
        this.username= username;
        this.password = password;
        this.email = email;
    }

    //Getters and Setters
    public String getUsername(){ return username; }
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){ return password; }
    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail(){ return email; }
    public void setEmail(String email){
        this.email = email;
    }
}
