package com.techcrack.quizservice.model;

import com.techcrack.quizservice.entity.Category;
import com.techcrack.quizservice.model.question.QuestionDto;

import java.util.List;

public record QuizQuestionResponse(Long id, String quizName, Category category, List<QuestionDto> questions) {
}
