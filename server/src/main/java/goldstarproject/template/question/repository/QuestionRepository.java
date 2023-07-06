package goldstarproject.template.question.repository;

import goldstarproject.template.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long>,QuestionRepositoryCustom {

}
