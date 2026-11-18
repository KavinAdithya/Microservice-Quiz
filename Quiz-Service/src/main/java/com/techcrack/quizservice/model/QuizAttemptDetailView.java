package com.techcrack.quizservice.model;

public record QuizAttemptDetailView(String quizName, long totalQuestions, long correctAnswer, long wrongAnswer) {
}
