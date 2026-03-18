package com.example.questionanswerblog.dto.response;

import java.time.LocalDateTime;

// DTO returned when sending answer data to the client
public class AnswerResponseDTO {

    private Long id;
    private String questionTitle;
    private String content;
    private String username;
    private LocalDateTime createdAt;

    public AnswerResponseDTO(){}

    public AnswerResponseDTO(Long id, String content, String questionTitle,
                             String username,
                             LocalDateTime createdAt){
        this.id = id;
        this.content = content;
        this.questionTitle = questionTitle;
        this.username= username;
        this.createdAt = createdAt;
    }

    //Getters and Setters
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public String getQuestionTitle(){ return questionTitle; }
    public void setQuestionTitle(String questionTitle){
        this.questionTitle = questionTitle;
    }

    public String getContent(){ return content; }
    public void setContent(String content){ this.content = content; }

    public String getUsername(){ return username; }
    public void setUsername(String username){ this.username = username; }

    public LocalDateTime getCreatedAt(){ return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt = createdAt; }

}
