package com.example.questionanswerblog.dto.request;

// DTO used when a user posts an answer
public class AnswerRequestDTO {

    private Long questionId;
    private String content;

    public AnswerRequestDTO(){}

    public AnswerRequestDTO(Long questionId, String content){
        this.questionId = questionId; // the question being answered
        this.content = content; // answer text
    }

    //Getters and Setters
    public Long getQuestionId(){ return questionId; }
    public void setQuestionId(Long questionId){ this.questionId = questionId;}

    public String getContent(){ return content; }
    public void setContent(String content){ this.content = content; }
}
