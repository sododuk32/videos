package goldstarproject.template.question.repository;

import goldstarproject.template.question.entity.Question;
import goldstarproject.template.recruit.entity.Recruit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepositoryCustom {

    void updateNoticeHeartCount(Question question, boolean b);

    Page<Question> findByQuestionContaining(String keyword, Pageable pageable);
    Page<Question> findByQuestionWriter(String keyword, Pageable pageable);


}
