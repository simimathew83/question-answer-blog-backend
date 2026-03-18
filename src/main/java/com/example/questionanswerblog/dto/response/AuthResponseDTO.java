package com.example.questionanswerblog.dto.response;

// DTO returned after successful authentication
// Contains the JWT token and user information
public class AuthResponseDTO {

    // JWT token sent to the client
    private String token;

    // DTO containing safe user information
    private UserDTO userDTO;

    // No-argument constructor required for serialization/deserialization
    public AuthResponseDTO(){}

    // Constructor used to quickly create the response object
    public AuthResponseDTO(String token, UserDTO userDTO){
        this.token = token;
        this.userDTO = userDTO;

    }

    //Getters and Setters
    public String getToken(){return token;}
    public void setToken(String token){this.token = token;}


    public UserDTO getUserDTO(){return userDTO;}
    public void setUserDTO(UserDTO userDTO){
        this.userDTO = userDTO;
    }
}
