package com.example.questionanswerblog.controller;

import com.example.questionanswerblog.dto.request.AnswerRequestDTO;
import com.example.questionanswerblog.dto.response.AnswerResponseDTO;
import com.example.questionanswerblog.exceptions.ResourceNotFoundException;
import com.example.questionanswerblog.exceptions.UnauthorizedException;
import com.example.questionanswerblog.mapper.AnswerMapper;
import com.example.questionanswerblog.model.Answer;
import com.example.questionanswerblog.model.Question;
import com.example.questionanswerblog.model.User;
import com.example.questionanswerblog.service.AnswerService;
import com.example.questionanswerblog.service.AuthService;
import com.example.questionanswerblog.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;
    private final AuthService authService;
    private final QuestionService questionService;
    private final AnswerMapper answerMapper;

    public AnswerController(AnswerService answerService,
                            AuthService authService,
                            QuestionService questionService,
                            AnswerMapper answerMapper){

        this.answerService = answerService;
        this.authService = authService;
        this.questionService = questionService;
        this.answerMapper = answerMapper;
    }

    //Post a new answer to a question
    @PostMapping
    public AnswerResponseDTO createAnswer(@RequestBody AnswerRequestDTO request){

        User currentUser = authService.getCurrentUser();
        Question question = questionService.getQuestionById(request.getQuestionId());

        Answer answer = answerService.createAnswer(request.getContent(),question, currentUser);

        return answerMapper.convertToDTO(answer);

    }

    //Get all answers for a question
    @GetMapping("/question/{questionId}")
    public List<AnswerResponseDTO> getAnswersByQuestion(@PathVariable Long questionId){

        Question question = questionService.getQuestionById(questionId);

        List <Answer> answers = answerService.getAnswersByQuestion(question);

        return answerMapper.convertToListDTO(answers);

    }

    // Get all answers posted by the current user
    @GetMapping("/me")
    public List<AnswerResponseDTO> getAnswersByUser(){

        User currentUser = authService.getCurrentUser();
        List <Answer> answers = answerService.getAnswersByUser(currentUser);
        return answerMapper.convertToListDTO(answers);
    }

    //Get answer by ID
    @GetMapping("/{id}")
    public AnswerResponseDTO getAnswerById(@PathVariable Long id){

        Answer answer = answerService.getAnswerById(id);
        return answerMapper.convertToDTO(answer);
    }

    // Delete an answer (admin only)
    @DeleteMapping("/{answerId}")
    public ResponseEntity<String> deleteAnswer(@PathVariable Long answerId){

        User currentUser = authService.getCurrentUser();
        try {
            answerService.deleteAnswer(answerId, currentUser);
            return ResponseEntity.ok("Answer deleted successfully!");

        }catch (UnauthorizedException ex){
            return ResponseEntity.status(403).body(ex.getMessage());

        }catch(ResourceNotFoundException ex){
            return ResponseEntity.status(404).body(ex.getMessage());
        }

        /*} catch(RuntimeException ex) {
            // Catch exceptions thrown by the service layer and return an appropriate HTTP response
            return ResponseEntity.status(404).body(ex.getMessage());
        }*/

    }
}
