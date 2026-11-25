package com.techcrack.quizservice.service;

import com.techcrack.quizservice.entity.Quiz;
import com.techcrack.quizservice.kafka.NotificationPublish;
import com.techcrack.quizservice.model.EmailNotificationModel;
import com.techcrack.quizservice.model.QuizResultResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationPublishService {
    private NotificationPublish notificationPublish;

    public boolean publishEmailNotificationFromQuizSubmission(Quiz quiz, QuizResultResponse response) {
        String body = "Quiz Result" +
                "\nQuiz Name : " + quiz.getName() +
                "\nAttempt Result" +
                "\nTotal Questions : " + response.totalQuestions() +
                "\nCorrect Answers : " + response.correctAnswers() +
                "\nWrong Answers : " + response.wrongAnswers();

        EmailNotificationModel model = new EmailNotificationModel("kavin.3x3@gmail.com",  "Quiz Result", body);

        return notificationPublish.publishNotify(model);
    }
}
