package com.example.questionanswerblog.dto.request;

// DTO used when a user submits a new question
public class QuestionRequestDTO {

    private String title; // title of the question
    private String content; // question body/content

    public QuestionRequestDTO(){}

    public QuestionRequestDTO(String title, String content){
        this.title = title;
        this.content = content;
    }

    //Getters and Setters
    public String getTitle(){ return title; }
    public void setTitle(String title){ this.title = title; }

    public String getContent(){ return content; }
    public void setContent(String content){ this.content = content; }
}
