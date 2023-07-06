package goldstarproject.template.question.service;

public interface QuestionService {
    void getViewCount(Long questionId);
    Long getTotalCommentCount(Long questionId);
}
