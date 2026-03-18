package com.example.questionanswerblog.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // unique ID for the question

    @Column(nullable = false)
    private String title; // question title

    @Column(nullable=false, columnDefinition = "TEXT")
    private String content; // question content

    @ManyToOne
    @JoinColumn(name = "user_id",nullable= false)
    private User user; // the user who posted this question

    @OneToMany(mappedBy = "question", cascade= CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>(); // list of answers to this question

    private LocalDateTime createdAt = LocalDateTime.now(); // timestamp

    public Question(){}

    public Question(String title, String content, User user){
        this.title = title;
        this.content = content;
        this.user = user;
    }

    //Getters and Setters
    public Long getId(){return id;}
    public void setId(Long id){ this.id = id; }

    public String getTitle(){return title;}
    public void setTitle(String title){ this.title = title; }

    public String getContent(){return content;}
    public void setContent(String content){ this.content = content; }

    public User getUser(){return user;}
    public void setUser(User user){ this.user = user; }

    public List<Answer> getAnswers(){return answers;}
    public void setAnswers(List<Answer> answers){ this.answers = answers; }

    public LocalDateTime getCreatedAt(){ return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt = createdAt; }

}
