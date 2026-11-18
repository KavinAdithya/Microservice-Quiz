package com.techcrack.quizservice.model;

import com.techcrack.quizservice.entity.Quiz;

public record QuizAttemptDetails(Quiz quiz, long totalQuestions, long correctAnswers, long userId) {
}
