package com.techcrack.quizservice.model;

public record PaginationModel(int page, int size, String sortBy, String direction) {
}
