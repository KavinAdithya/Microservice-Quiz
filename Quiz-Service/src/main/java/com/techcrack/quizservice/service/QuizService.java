package com.techcrack.quizservice.service;

import com.techcrack.quizservice.entity.Category;
import com.techcrack.quizservice.entity.Quiz;
import com.techcrack.quizservice.feign.QuestionServiceInterface;
import com.techcrack.quizservice.model.QuizCreateRandomRequest;
import com.techcrack.quizservice.model.QuizCreateRequest;
import com.techcrack.quizservice.model.question.QuestionDto;
import com.techcrack.quizservice.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    private final QuizRepository repository;
    private final QuestionServiceInterface questionService;

    public QuizService(QuizRepository quizRepository, QuestionServiceInterface questionService) {
        this.repository = quizRepository;
        this.questionService = questionService;
    }

    public boolean createQuiz(QuizCreateRequest request) {
        List<QuestionDto> questions = questionService.findAllQuestions().getBody();

        assert questions != null;
        List<Long> questionIds = questions
                .stream()
                .map(QuestionDto::id)
                .toList();

        save(request.quizName(), Category.UNKNOWN, questionIds);

        return true;
    }

    public boolean createQuizRandom(QuizCreateRandomRequest request) {
        List<Long> questions = questionService.getRandomQuestionsByCategory(request.category(), request.questions()).getBody();

        save(request.quizName(), request.category(), questions);

        return true;
    }

    public void save(String quizName, Category category, List<Long> questionIds) {
        Quiz quiz = new Quiz();
        quiz.setName(quizName);
        quiz.setQuestionIds(questionIds);
        quiz.setCategory(category);

        repository.save(quiz);
    }

    public List<Quiz> getAllQuizzes() {
        return repository.findAll();
    }
}
