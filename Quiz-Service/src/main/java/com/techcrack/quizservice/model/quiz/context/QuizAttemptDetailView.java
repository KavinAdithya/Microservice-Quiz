package com.techcrack.quizservice.model.quiz.context;

public record QuizAttemptDetailView(String quizName, long totalQuestions, long correctAnswer, long wrongAnswer) {
}
