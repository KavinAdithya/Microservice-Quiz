package com.techcrack.quizservice.service;

import com.techcrack.quizservice.entity.Category;
import com.techcrack.quizservice.entity.Quiz;
import com.techcrack.quizservice.feign.AuthenticationServiceClient;
import com.techcrack.quizservice.feign.QuestionServiceClient;
import com.techcrack.quizservice.model.*;
import com.techcrack.quizservice.model.question.QuestionDto;
import com.techcrack.quizservice.repository.QuizRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class QuizService {
    private final QuizRepository repository;

    private final QuizAttemptService quizAttemptService;

    private final QuestionServiceClient questionClient;
    private final AuthenticationServiceClient authClient;

    public QuizService(QuizRepository quizRepository,
                       QuestionServiceClient questionService,
                       QuizAttemptService quizAttemptService,
                       AuthenticationServiceClient authClient) {
        this.repository = quizRepository;
        this.questionClient = questionService;
        this.quizAttemptService = quizAttemptService;
        this.authClient = authClient;
    }

    public boolean createQuiz(QuizCreateRequest request) {
        List<QuestionDto> questions = questionClient.findAllQuestions().getBody();

        assert questions != null;
        List<Long> questionIds = questions
                .stream()
                .map(QuestionDto::id)
                .toList();

        save(request.quizName(), Category.UNKNOWN, questionIds);

        return true;
    }

    public boolean createQuizRandom(QuizCreateRandomRequest request) {
        List<Long> questions = questionClient.getRandomQuestionsByCategory(request.category(), request.questions()).getBody();

        save(request.quizName(), request.category(), questions);

        return true;
    }

    public void save(String quizName, Category category, List<Long> questionIds) {
        Quiz quiz = new Quiz();
        quiz.setName(quizName);
        quiz.setQuestionIds(questionIds);
        quiz.setCategory(category);

        repository.save(quiz);
    }

    public List<Quiz> getAllQuizzes() {
        return repository.findAll();
    }

    public QuizQuestionResponse getRandomQuizByCategory(Category category, PaginationModel pagination) {

        if (!Objects.equals(pagination.sortBy(), "id")) {
            throw new RuntimeException("Invalid Sorting column");
        }

        Sort sort = Objects.equals(pagination.direction(), "asc") ?
                    Sort.by(pagination.sortBy()).ascending() :
                    Sort.by(pagination.sortBy()).descending();

        Quiz quiz = repository.getRandomQuizByCategory(category.name());

        List<Long> questionIds = quiz.getQuestionIds()
                                        .stream()
                                        .sorted()
                                        .skip(((long) pagination.page() - 1) * pagination.size())
                                        .limit(pagination.size())
                                        .toList();

        System.out.println("QUESTION IDS : " + questionIds);
        List<QuestionDto> questionDtos = questionClient.getAllQuestions(questionIds).getBody();

        return new QuizQuestionResponse(quiz.getId(), quiz.getName(), category, questionDtos);
    }

    public QuizResultResponse submitAnswers(QuizAnswerSubmissionRequest submissionRequest) {
        Quiz quiz = repository.findById(submissionRequest.quizId())
                .orElseThrow(() -> new RuntimeException("Invalid Quiz Id"));

        Long totalCorrectAnswers = questionClient.submitAnswers(submissionRequest.answerSubmitRequests()).getBody();

        if (totalCorrectAnswers == null)
            throw new RuntimeException("Failed to evaluate quiz. try again");

        Long currentUserId = authClient.getCurrentUserId().getBody();

        if (currentUserId == null)
            throw new RuntimeException("Failed to fetch current user details. Try again later");

        boolean isAttemptCreated = quizAttemptService.createQuizAttemptFromAnswerSubmission(new QuizAttemptDetails(quiz, quiz.getQuestionIds().size(), totalCorrectAnswers, currentUserId));

        if (!isAttemptCreated)
            throw new RuntimeException("Failed to create attempt");

        return new QuizResultResponse(quiz.getQuestionIds().size(), totalCorrectAnswers, quiz.getQuestionIds().size() - totalCorrectAnswers);
    }
}
