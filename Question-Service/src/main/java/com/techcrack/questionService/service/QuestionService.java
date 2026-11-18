package com.techcrack.questionService.service;

import com.techcrack.questionService.dto.QuestionAnswerSubmissionRequest;
import com.techcrack.questionService.dto.question.QuestionDto;
import com.techcrack.questionService.entity.Category;
import com.techcrack.questionService.entity.Question;
import com.techcrack.questionService.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    public List<Question> findAllQuestions() {
        return repository.findAll();
    }

    public Question findQuestionById(long questionId) {
        return repository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Invalid Question Id"));
    }

    public List<Long> getRandomQuestionsByCategory(Category category, int count) {
        return repository.getRandomQuestionsByCategory(category.name(), count);
    }

    public List<Question> findAllQuestionsById(List<Long> questionIds) {

        return repository.getAllQuestionsById(questionIds);
    }

    public long submitAnswers(List<QuestionAnswerSubmissionRequest> submissionRequests) {
        Map<Long, QuestionAnswerSubmissionRequest> map = submissionRequests
                .stream()
                .collect(Collectors.toMap(QuestionAnswerSubmissionRequest::questionId, x -> x));

        return repository
                .findAll()
                .stream()
                .filter(question -> map.containsKey(question.getId()) && question.getCorrectOption().getId() == map.get(question.getId()).answerOptionId())
                .count();
    }
}
