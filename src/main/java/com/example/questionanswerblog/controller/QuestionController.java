package com.example.questionanswerblog.controller;

import com.example.questionanswerblog.dto.request.QuestionRequestDTO;
import com.example.questionanswerblog.dto.response.QuestionResponseDTO;
import com.example.questionanswerblog.exceptions.ResourceNotFoundException;
import com.example.questionanswerblog.exceptions.UnauthorizedException;
import com.example.questionanswerblog.mapper.QuestionMapper;
import com.example.questionanswerblog.model.Question;
import com.example.questionanswerblog.model.User;
import com.example.questionanswerblog.service.AuthService;
import com.example.questionanswerblog.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/questions") // Base URL for all question-related endpoints
public class QuestionController {

    // Service layer dependencies
    private final QuestionService questionService; // Handles question business logic
    private final AuthService authService; // Provides current authenticated user
    private final QuestionMapper questionMapper; // Maps entities to DTOs

    // Constructor-based dependency injection (Spring injects these beans automatically)
    public QuestionController(QuestionService questionService, AuthService authService,
                                QuestionMapper questionMapper) {
        this.questionService = questionService;
        this.authService = authService;
        this.questionMapper = questionMapper;
    }

    // ---------------- CREATE QUESTION ----------------
    @PostMapping
    public QuestionResponseDTO createQuestion(@RequestBody QuestionRequestDTO request){

        User user = authService.getCurrentUser();

        // Call service to create a new question
        Question question = questionService.createQuestion(request.getTitle(),
                                                                    request.getContent(),
                                                                    user);
        return questionMapper.convertToDTO(question);
    }

    // ---------------- GET ALL QUESTIONS ----------------
    @GetMapping
    public List<QuestionResponseDTO> getAllQuestions(){
        // Fetch all questions from database
        List<Question> questions = questionService.getAllQuestions();

        return questionMapper.convertToListDTO(questions);
    }

    // ---------------- GET QUESTION BY ID ----------------
    @GetMapping("/{id}")
    public QuestionResponseDTO getQuestionById(@PathVariable Long id) {

        // Fetch single question, service throws exception if not found
        Question question = questionService.getQuestionById(id);

        return questionMapper.convertToDTO(question);

    }

    // ---------------- GET QUESTIONS FOR CURRENT USER ----------------
    @GetMapping("/my-questions")
    public List<QuestionResponseDTO> getQuestionsByCurrentUser() {

        User currentUser = authService.getCurrentUser();

        // Fetch questions authored by this user
        List<Question> questions = questionService.getQuestionsByUser(currentUser);

        return questionMapper.convertToListDTO(questions);
    }

    // ---------------- SEARCH QUESTIONS BY KEYWORD ----------------
    @GetMapping("/search")
    public List<QuestionResponseDTO> searchQuestions(@RequestParam String keyword){

        // Search questions containing the keyword
        List<Question> questions = questionService.searchQuestions(keyword);

        if(keyword == null || keyword.isEmpty()){
            return questionMapper.convertToListDTO(questionService.getAllQuestions());
        }
        return questionMapper.convertToListDTO(questions);

    }

    // ---------------- EDIT QUESTION ----------------
    @PutMapping("/{id}")
    public QuestionResponseDTO editQuestion(@PathVariable Long id,
                                            @RequestBody QuestionRequestDTO request){

        User user = authService.getCurrentUser();

        // Call service to update the question
        Question question = questionService.editQuestion(id, request.getTitle(),
                                            request.getContent(), user);

        return questionMapper.convertToDTO(question);

    }

    // ---------------- DELETE QUESTION ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long id){

        User user = authService.getCurrentUser();

        try {
            // Delete the question via service
            questionService.deleteQuestion(id, user);
            return ResponseEntity.ok("Question deleted successfully");

        }catch(UnauthorizedException ex){
            return ResponseEntity.status(403).body(ex.getMessage());

        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(404).body(ex.getMessage());
        }


        /*}catch(RuntimeException ex){
            // Catch exceptions thrown by the service layer and return an appropriate HTTP response
            return ResponseEntity.status(404).body(ex.getMessage());
        }*/

    }
}
