package com.techcrack.quizservice.service;

import com.techcrack.quizservice.feign.QuestionServiceClient;
import com.techcrack.quizservice.model.QuizAnswerSubmissionRequest;
import com.techcrack.quizservice.model.question.QuestionDto;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class QuestionServiceRetryClient {

    private final QuestionServiceClient questionClient;

    public QuestionServiceRetryClient(QuestionServiceClient questionClient) {
        this.questionClient = questionClient;
    }

    @Retry(name = "questionService")
    @CircuitBreaker(
            name="questionService",
            fallbackMethod = "correctAnswerFallback"
    )
    public Long findCorrectAnswerScore(QuizAnswerSubmissionRequest submissionRequest) {
        System.out.println("Calling Question Service...");
        return questionClient.submitAnswers(submissionRequest.answerSubmitRequests()).getBody();
    }

    public Long correctAnswerFallback(
            QuizAnswerSubmissionRequest submissionRequest,
            Throwable throwable) {

        System.out.println("Circuit Breaker fallback executed!");
        System.out.println("Reason: " + throwable.getMessage());

        throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE,
                "Question Service is currently unavailable"
        );
    }

    @Retry(name = "questionService")
    @CircuitBreaker(
            name = "questionService",
            fallbackMethod = "getAllQuestionsFallback"
    )
    @RateLimiter(
            name = "questionService",
            fallbackMethod = "getAllQuestionRateLimitFallBack"
    )
    @Bulkhead(
            name = "questionService",
            fallbackMethod = "getAllQuestionRequestOverload"
    )
    public List<QuestionDto> getAllQuestions(List<Long> questionIds) {

        return questionClient.getAllQuestions(questionIds).getBody();
    }

    public List<QuestionDto> getAllQuestionsFallback(
            List<Long> questionIds,
            Throwable throwable) throws Throwable {

        System.out.println("FALLBACK EXECUTED!");
        System.out.println(throwable.getMessage());

        throw throwable;
    }

    public List<QuestionDto> getAllQuestionRateLimitFallBack(
            List<Long> questionIds,
            Throwable throwable) throws Throwable {

        System.out.println("RATE LIMIT REJECTED");
        System.out.println(throwable.getMessage());

        throw throwable;
    }

    public List<QuestionDto> getAllQuestionRequestOverload(
            List<Long> questionIds,
            Throwable throwable) throws Throwable {

        System.out.println("BULK HEAD REJECTED FOR GET ALL QUESTION");
        System.out.println(throwable.getMessage());

        throw throwable;
    }


}
