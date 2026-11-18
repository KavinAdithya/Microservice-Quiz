package com.techcrack.quizservice.model;

public record QuizResultResponse(long totalQuestions, long correctAnswers, long wrongAnswers) {
}
