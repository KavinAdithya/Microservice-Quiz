package com.techcrack.quizservice.model.quiz.request;

import com.techcrack.quizservice.entity.Category;

public record QuizCreateRandomRequest(Category category, String quizName, int questions) {
}
