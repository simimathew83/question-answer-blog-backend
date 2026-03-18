package com.example.questionanswerblog.repository;

import com.example.questionanswerblog.model.Answer;
import com.example.questionanswerblog.model.Question;
import com.example.questionanswerblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    // Find all answers for a specific question
    List<Answer> findByQuestion(Question question);

    // Find all answers posted by a particular user
    List<Answer> findByUser(User user);
}
