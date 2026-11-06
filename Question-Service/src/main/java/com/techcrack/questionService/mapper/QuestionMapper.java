package com.techcrack.questionService.mapper;

import com.techcrack.questionService.dto.question.QuestionDto;
import com.techcrack.questionService.entity.Question;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionMapper {

    private final OptionMapper optionMapper;

    public QuestionMapper(OptionMapper optionMapper) {
        this.optionMapper = optionMapper;
    }

    public QuestionDto mapToQuestionDto(Question question) {
        return new QuestionDto(
                question.getId(),
                question.getCategory(),
                question.getQuestion(),
                optionMapper.mapToOptionDtos(question.getOptions())
        );
    }

    public List<QuestionDto> mapToQuestionDtos(List<Question> questions) {
        return questions
                .stream()
                .map(this::mapToQuestionDto)
                .toList();
    }
}
