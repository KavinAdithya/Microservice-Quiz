package com.techcrack.quizservice.notification.email;

import com.techcrack.quizservice.entity.Quiz;
import com.techcrack.quizservice.feign.AuthenticationServiceClient;
import com.techcrack.quizservice.notification.email.abstraction.EmailProducer;
import com.techcrack.quizservice.model.quiz.response.QuizResultResponse;
import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class QuizEmailProducerStrategy implements EmailProducer {

    private final AuthenticationServiceClient authenticationServiceClient;
    private final String subject;
    private final Quiz quiz;
    private final QuizResultResponse response;

    @Override
    public String to() {
        return Objects.requireNonNull(authenticationServiceClient.getCurrentUser().getBody()).email();
    }

    @Override
    public String subject() {
        return subject;
    }

    @Override
    public String body() {
        return "Quiz Result" +
                "\nQuiz Name : " + quiz.getName() +
                "\nAttempt Result" +
                "\nTotal Questions : " + response.totalQuestions() +
                "\nCorrect Answers : " + response.correctAnswers() +
                "\nWrong Answers : " + response.wrongAnswers();
    }
}
