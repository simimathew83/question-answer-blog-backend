package com.example.questionanswerblog.dto.response;

/* * UserDTO (Data Transfer Object)
* This class is used to transfer user data between layers
* (for example: Service → Controller → API response). *
* It intentionally contains only the fields that we want to expose to the outside world.
* Sensitive information such as passwords should NOT be included in DTOs. */
public class UserDTO {

    Long id;
    String username;
    String email;
    String role;

    // No-argument constructor
    public UserDTO(){}

    /* * Parameterized constructor
    * Used when we want to create a UserDTO object quickly by
    * passing all required values at once. */
    public UserDTO(Long id, String username, String email, String role){
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    //Getters and Setters
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id;}

    public String getUsername(){ return username; }
    public void setUsername(String username){ this.username = username;}

    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email;}

    public String getRole(){ return role; }
    public void setRole(String role){ this.role = role;}

}
