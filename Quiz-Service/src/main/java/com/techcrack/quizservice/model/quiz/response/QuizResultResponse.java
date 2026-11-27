package com.techcrack.quizservice.model.quiz.response;

public record QuizResultResponse(long totalQuestions, long correctAnswers, long wrongAnswers) {
}
