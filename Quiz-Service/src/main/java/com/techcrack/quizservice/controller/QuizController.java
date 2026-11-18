package com.techcrack.quizservice.controller;

import com.techcrack.quizservice.entity.Category;
import com.techcrack.quizservice.entity.Quiz;
import com.techcrack.quizservice.model.*;
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
    public ResponseEntity<List<Quiz>> getAllQuiz() {
        List<Quiz> quizzes = quizService.getAllQuizzes();

        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/quiz/random")
    public ResponseEntity<QuizQuestionResponse> getQuizRandomByCategory(
                @RequestParam(defaultValue = "JAVA") Category category,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(defaultValue = "id") String sortBy,
                @RequestParam(defaultValue = "asc") String direction
    ) {

        PaginationModel paginationModel = new PaginationModel(page, size, sortBy, direction);


        QuizQuestionResponse response = quizService.getRandomQuizByCategory(category, paginationModel);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/quiz/answer/submission")
    public ResponseEntity<QuizResultResponse> submitAnswer(@RequestBody QuizAnswerSubmissionRequest submissionRequest) {
        QuizResultResponse response = quizService.submitAnswers(submissionRequest);

        return ResponseEntity.ok(response);
    }
}
