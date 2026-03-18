package com.example.questionanswerblog.dto.response;

import java.time.LocalDateTime;
import java.util.List;

// DTO returned when retrieving questions
public class QuestionResponseDTO {

    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private List<AnswerResponseDTO> answers;


    public QuestionResponseDTO(){}

    public QuestionResponseDTO(Long id, String title, String content,
                               String username, LocalDateTime createdAt,
                               List<AnswerResponseDTO> answers){
        this.id = id;
        this.title = title;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
        this.answers = answers;
    }

    //Getters and Setters
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id;}

    public String getTitle(){ return title; }
    public void setTitle(String title){ this.title = title; }

    public String getContent(){ return content; }
    public void setContent(String content){ this.content = content;}

    public String getUsername(){ return username; }
    public void setUsername(String username){ this.username = username;}

    public LocalDateTime getCreatedAt(){ return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt = createdAt;}

    public List<AnswerResponseDTO> getAnswers(){ return answers; }
    public void setAnswers(List<AnswerResponseDTO> answers){ this.answers = answers;}

}
