package com.example.questionanswerblog.service;

import com.example.questionanswerblog.model.Question;
import com.example.questionanswerblog.model.User;
import com.example.questionanswerblog.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }

    // Create a new question
    public Question createQuestion(String title, String content, User user){

        Question question = new Question();

        question.setTitle(title);
        question.setContent(content);
        question.setUser(user);
        return questionRepository.save(question);
    }

    // Fetch all questions
    public List<Question> getAllQuestions(){
        return questionRepository.findAll();
    }

    // Fetch a single question by ID
    public Question getQuestionById(Long id){
        return questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found!"));
    }

    // Fetch all questions posted by a user
    public List<Question> getQuestionsByUser(User user){
        return questionRepository.findByUser(user);
    }

    // Search questions by keyword in title or content
    public List<Question> searchQuestions(String keyword){
        return questionRepository.searchQuestions(keyword);
    }

    // Edit a question
    public Question editQuestion(Long questionId, String newTitle,
                                 String newContent, User currentUser){

        Question question = getQuestionById(questionId);

        // Only the owner or the ADMIN can edit
        if(!question.getUser().getId().equals(currentUser.getId())
            && !"ADMIN".equalsIgnoreCase(currentUser.getRole())){
            throw new RuntimeException("You are Not allowed to edit this question");
        }

        question.setTitle(newTitle);
        question.setContent(newContent);
        return questionRepository.save(question);
    }

    // Delete a question
    public void deleteQuestion(Long questionId, User currentUser){

        Question question = getQuestionById(questionId);

        // Only the owner or the ADMIN can delete
        if(!question.getUser().getId().equals(currentUser.getId())
            && !"ADMIN".equalsIgnoreCase(currentUser.getRole())){
            throw new RuntimeException("You are Not authorized to delete the question!");
        }

        questionRepository.delete(question);

    }
}
