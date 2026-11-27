package com.techcrack.quizservice.model.quiz.request;

import com.techcrack.quizservice.model.question.QuestionAnswerSubmissionRequest;

import java.util.List;

public record QuizAnswerSubmissionRequest(Long quizId, List<QuestionAnswerSubmissionRequest> answerSubmitRequests) {
}
