package com.techcrack.questionService.dto.question;

import com.techcrack.questionService.dto.option.OptionDto;
import com.techcrack.questionService.entity.Category;

import java.util.List;

public record QuestionDto(Long id, Category category, String question, List<OptionDto> options) {
}
