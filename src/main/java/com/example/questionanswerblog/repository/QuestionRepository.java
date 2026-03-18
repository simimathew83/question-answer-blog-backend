package com.example.questionanswerblog.repository;

import com.example.questionanswerblog.model.Question;
import com.example.questionanswerblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Marks this as a Spring-managed repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    // Find all questions posted by a particular user
    List<Question> findByUser(User user);

    // Custom query to search questions by keyword in title or content
    @Query("SELECT q from Question q " +
            "WHERE (:keyword IS NULL " +
            "OR LOWER(q.title) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            "OR LOWER(q.content) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Question> searchQuestions(@Param("keyword") String keyword);

}
