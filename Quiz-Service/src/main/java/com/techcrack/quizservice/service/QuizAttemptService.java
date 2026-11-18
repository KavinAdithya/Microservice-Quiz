package com.techcrack.quizservice.service;

import com.techcrack.quizservice.entity.QuizAttempt;
import com.techcrack.quizservice.feign.AuthenticationServiceClient;
import com.techcrack.quizservice.model.QuizAttemptDetailView;
import com.techcrack.quizservice.model.QuizAttemptDetails;
import com.techcrack.quizservice.repository.QuizAttemptRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizAttemptService {
    private final QuizAttemptRepository repository;

    private final AuthenticationServiceClient authClient;

    public QuizAttemptService(QuizAttemptRepository repository,
                              AuthenticationServiceClient authClient) {
        this.repository = repository;
        this.authClient = authClient;
    }

    public boolean createQuizAttemptFromAnswerSubmission(QuizAttemptDetails details) {
        QuizAttempt quizAttempt = new QuizAttempt();

        quizAttempt.setQuiz(details.quiz());
        quizAttempt.setCorrectAnswer(details.correctAnswers());
        quizAttempt.setTotalQuestions(details.totalQuestions());
        quizAttempt.setUserId(details.userId());

        repository.save(quizAttempt);

        return true;
    }

    public List<QuizAttemptDetailView> getAllCurrentUserQuizAttempts() {
        Long currentUserId = authClient.getCurrentUserId().getBody();

        assert currentUserId != null;

        return repository
                .findByUserId(currentUserId)
                .stream()
                .map(quizAttempt -> new QuizAttemptDetailView(
                       quizAttempt.getQuiz().getName(),
                       quizAttempt.getTotalQuestions(),
                       quizAttempt.getCorrectAnswer(),
                       quizAttempt.getTotalQuestions() - quizAttempt.getCorrectAnswer()
               ))
                .toList();
    }
}
