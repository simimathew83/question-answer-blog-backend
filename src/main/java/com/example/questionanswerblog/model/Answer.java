package com.example.questionanswerblog.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // unique ID for the answer

    @Column(nullable=false, columnDefinition = "TEXT")
    private String content; // answer content

    @ManyToOne
    @JoinColumn(name = "question_id",nullable = false)
    private Question question; // the question this answer belongs to

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // the user who posted this answer

    private LocalDateTime createdAt = LocalDateTime.now(); // timestamp

    public Answer(){}

    public Answer(String content, Question question, User user){
        this.content = content;
        this.question = question;
        this.user = user;
    }

    //Getters and Setters
    public Long getId(){ return id; }
    public void setId(Long id){ this.id= id; }

    public String getContent(){ return content; }
    public void setContent(String content){ this.content= content; }

    public Question getQuestion(){ return question; }
    public void setQuestion(Question question){ this.question= question; }

    public User getUser(){ return user; }
    public void setUser(User user){ this.user= user; }

    public LocalDateTime getCreatedAt(){ return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt= createdAt; }

}
