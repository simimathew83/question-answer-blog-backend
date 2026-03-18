package com.example.questionanswerblog.dto.request;

// DTO used to receive login credentials from the client
public class LoginRequestDTO {

    private String username;
    private String password;

    public LoginRequestDTO(){} // No-argument constructor required for JSON deserialization

    // Constructor used to create a LoginRequest object with username and password
    public LoginRequestDTO(String username, String password){
        this.username= username;
        this.password = password;
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
}
