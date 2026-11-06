package com.techcrack.quizservice.model.question;


import com.techcrack.quizservice.entity.Category;
import com.techcrack.quizservice.model.option.OptionDto;

import java.util.List;

public record QuestionDto(Long id, Category category, String question, List<OptionDto> options) {
}
