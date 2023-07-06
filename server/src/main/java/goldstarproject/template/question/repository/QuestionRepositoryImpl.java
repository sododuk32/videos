package goldstarproject.template.question.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import goldstarproject.template.question.entity.QQuestion;
import goldstarproject.template.question.entity.Question;
import goldstarproject.template.recruit.entity.Recruit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static goldstarproject.template.question.entity.QQuestion.*;
import static goldstarproject.template.recruit.entity.QRecruit.recruit;


@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void updateNoticeHeartCount(Question question, boolean b) {
        if (b) {
            queryFactory.update(QQuestion.question)
                    .set(QQuestion.question.likeCount, QQuestion.question.likeCount.add(1))
                    .where(QQuestion.question.eq(question))
                    .execute();
        } else {
            queryFactory.update(QQuestion.question)
                    .set(QQuestion.question.likeCount, QQuestion.question.likeCount.subtract(1))
                    .where(QQuestion.question.eq(question))
                    .execute();
        }
    }

    @Override
    public Page<Question> findByQuestionContaining(String keyword, Pageable pageable) {
        QueryResults<Question> searchResults = queryFactory.selectFrom(question)
                .where(question.title.like("%" + keyword + "%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(searchResults.getResults(), pageable, searchResults.getTotal());
    }


    @Override
    public Page<Question> findByQuestionWriter(String username, Pageable pageable) {
        QueryResults<Question> searchResults = queryFactory.selectFrom(question)
                .where(question.writer.username.like("%" + username + "%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(searchResults.getResults(),pageable, searchResults.getTotal());
    }
}
