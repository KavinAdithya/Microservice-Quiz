package com.techcrack.quizservice.model;

import com.techcrack.quizservice.entity.Category;

public record QuizCreateRandomRequest(Category category, String quizName, int questions) {
}
