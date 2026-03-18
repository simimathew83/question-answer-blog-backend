package com.example.questionanswerblog.model;

import jakarta.persistence.*; // Imports JPA annotations

@Entity // Marks this class as a database entity
@Table(name="users") // Maps this entity to the "users" table
public class User {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates ID values in the database
    private Long id;


    @Column(unique = true, nullable = false) // Username must be unique and cannot be null
    private String username;

    @Column(unique = true, nullable = false) // Email must be unique and cannot be null
    private String email;

    @Column(nullable=false)
    private String password; // Stores the user's password

    //Role can be USER or ADMIN
    private String role = "USER"; // User role with default value USER

    public User(){} // No-argument constructor required by JPA

    // Constructor to create User object
    public User(String username, String password, String email, String role){
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    //Getters and Setters
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}


    public String getUsername(){return username;}
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){return password;}
    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail(){return email;}

    public void setEmail(String email){
        this.email = email;
    }

    public String getRole(){return role;}
    public void setRole(String role){
        this.role = role;
    }

}
