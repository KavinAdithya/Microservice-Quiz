package com.techcrack.quizservice.model.template;

public record PaginationModel(int page, int size, String sortBy, String direction) {
}
