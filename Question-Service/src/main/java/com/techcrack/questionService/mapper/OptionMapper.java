package com.techcrack.questionService.mapper;

import com.techcrack.questionService.dto.option.OptionDto;
import com.techcrack.questionService.entity.Option;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OptionMapper {
    public OptionDto mapToOptionDto(Option option) {
        return new OptionDto(option.getId(), option.getValue());
    }

    public List<OptionDto> mapToOptionDtos(List<Option> options) {
        return options
                .stream()
                .map(this::mapToOptionDto)
                .toList();
    }
}
