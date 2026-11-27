package com.techcrack.quizservice.controller;

import com.techcrack.quizservice.model.quiz.context.QuizAttemptDetailView;
import com.techcrack.quizservice.service.QuizAttemptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuizAttemptController {
    private final QuizAttemptService quizAttemptService;

    public QuizAttemptController(QuizAttemptService quizAttemptService) {
        this.quizAttemptService = quizAttemptService;
    }

    @RequestMapping("/quiz/attempts")
    public ResponseEntity<List<QuizAttemptDetailView>> getAllCurrentUserAttempts() {
        List<QuizAttemptDetailView> quizAttemptDetailViews = quizAttemptService.getAllCurrentUserQuizAttempts();

        return ResponseEntity.ok(quizAttemptDetailViews);
    }
}
