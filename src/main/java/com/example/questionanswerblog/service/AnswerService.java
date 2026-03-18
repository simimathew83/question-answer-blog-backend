package com.example.questionanswerblog.service;

import com.example.questionanswerblog.model.Answer;
import com.example.questionanswerblog.model.Question;
import com.example.questionanswerblog.model.User;
import com.example.questionanswerblog.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository){

        this.answerRepository = answerRepository;
    }

    // Create a new answer
    public Answer createAnswer(String content, Question question, User user){
        Answer answer = new Answer();

        answer.setContent(content);
        answer.setQuestion(question);
        answer.setUser(user);

        return answerRepository.save(answer);
    }

    // Fetch all answers for a particular question
    public List<Answer> getAnswersByQuestion(Question question){
        return answerRepository.findByQuestion(question);

    }

    // Fetch all answers by a user
    public List<Answer> getAnswersByUser(User currentUser){

        return answerRepository.findByUser(currentUser);
    }

    // Fetch a single answer by ID
    public Answer getAnswerById(Long id){

        return answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found!"));
    }

    public void deleteAnswer(Long id,User currentUser){

        // Fetch the answer
        Answer answer = getAnswerById(id);

        // Only admins can delete
        if(!"ADMIN".equalsIgnoreCase(currentUser.getRole())){
            throw new RuntimeException("You do NOT have ADMIN privileges delete the answer!");
        }

        // Delete the answer
        answerRepository.deleteById(id);

    }
}
