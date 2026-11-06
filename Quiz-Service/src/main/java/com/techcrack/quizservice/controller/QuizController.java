package com.techcrack.quizservice.controller;

import com.techcrack.quizservice.entity.Quiz;
import com.techcrack.quizservice.model.QuizCreateRandomRequest;
import com.techcrack.quizservice.model.QuizCreateRequest;
import com.techcrack.quizservice.model.QuizCreateResponse;
import com.techcrack.quizservice.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/quiz/create")
    public ResponseEntity<?> createQuiz(@RequestBody QuizCreateRequest request) {
        boolean isQuizCreated = quizService.createQuiz(request);

        if (!isQuizCreated)
            return ResponseEntity.internalServerError()
                    .body("Something went wrong");

        return ResponseEntity.ok(new QuizCreateResponse(request.quizName()));
    }

    @PostMapping("/quiz/create/random")
    public ResponseEntity<?> createQuizRandom(@RequestBody QuizCreateRandomRequest request) {
        boolean isQuizCreated = quizService.createQuizRandom(request);

        if (!isQuizCreated)
            return ResponseEntity.internalServerError()
                    .body("Something went wrong");

        return ResponseEntity.ok(new QuizCreateResponse(request.quizName()));
    }

    @GetMapping("/quizzes")
    public ResponseEntity<List<Quiz>> getAllQuestions() {
        List<Quiz> quizzes = quizService.getAllQuizzes();

        return ResponseEntity.ok(quizzes);
    }
}
