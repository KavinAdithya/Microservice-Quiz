package com.techcrack.questionService.repository;

import com.techcrack.questionService.entity.Category;
import com.techcrack.questionService.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = "SELECT id FROM Questions WHERE category = :category ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<Long> getRandomQuestionsByCategory(String category, int count);
}
