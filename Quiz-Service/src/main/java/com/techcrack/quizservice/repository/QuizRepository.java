package com.techcrack.quizservice.repository;

import com.techcrack.quizservice.entity.Category;
import com.techcrack.quizservice.entity.Quiz;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query(
            value = """
                        SELECT * FROM Quiz
                        WHERE category = :category
                        ORDER BY RAND() LIMIT 1
                    """,
            nativeQuery = true)
    Quiz getRandomQuizByCategory(String category);
}
