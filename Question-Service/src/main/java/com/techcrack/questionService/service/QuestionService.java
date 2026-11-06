package com.techcrack.questionService.service;

import com.techcrack.questionService.entity.Category;
import com.techcrack.questionService.entity.Question;
import com.techcrack.questionService.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
