package com.techcrack.quizservice.feign;

import com.techcrack.quizservice.entity.Category;
import com.techcrack.quizservice.model.question.QuestionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("Question-Service")
public interface QuestionServiceInterface {
    @GetMapping("/api/questions")
    ResponseEntity<List<QuestionDto>> findAllQuestions();

    @GetMapping("/api/questions/random")
    ResponseEntity<List<Long>> getRandomQuestionsByCategory(@RequestParam("category") Category category, @RequestParam("limit") int count);
}
