package com.techcrack.questionService.controller;

import com.techcrack.questionService.dto.QuestionAnswerSubmissionRequest;
import com.techcrack.questionService.dto.question.QuestionDto;
import com.techcrack.questionService.entity.Category;
import com.techcrack.questionService.entity.Question;
import com.techcrack.questionService.mapper.QuestionMapper;
import com.techcrack.questionService.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionController {

    private final QuestionMapper questionMapper;
    private final QuestionService service;

    public QuestionController(QuestionService service, QuestionMapper questionMapper) {
        this.service = service;
        this.questionMapper = questionMapper;
    }

    @GetMapping("/questions")
    public ResponseEntity<List<QuestionDto>> findAllQuestions() {
        List<Question> questions = service.findAllQuestions();
        List<QuestionDto> questionDtos = questionMapper.mapToQuestionDtos(questions);
        return new ResponseEntity<>(questionDtos, HttpStatus.OK);
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<QuestionDto> findQuestionById(@PathVariable Long id) {
        Question question = service.findQuestionById(id);

        return new ResponseEntity<>(questionMapper.mapToQuestionDto(question), HttpStatus.OK);
    }

    @GetMapping("/questions/random")
    public ResponseEntity<List<Long>> getRandomQuestionsByCategory(@RequestParam("category") Category category, @RequestParam("limit") int count) {
        return ResponseEntity.ok(service.getRandomQuestionsByCategory(category, count));
    }

    @PostMapping("/questions")
    public ResponseEntity<List<QuestionDto>> getAllQuestions(@RequestBody List<Long> questionIds) {
        List<Question> questionDtos = service.findAllQuestionsById(questionIds);

        return ResponseEntity.ok(questionMapper.mapToQuestionDtos(questionDtos));
    }

    @PostMapping("/questions/answer/submission")
    public ResponseEntity<Long> submitAnswers(@RequestBody List<QuestionAnswerSubmissionRequest> submissionRequests) {
        long result = service.submitAnswers(submissionRequests);

        return ResponseEntity.ok(result);
    }
}
