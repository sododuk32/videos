package goldstarproject.template.recruit.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import goldstarproject.template.notice.entity.Notice;
import goldstarproject.template.recruit.entity.QRecruit;
import goldstarproject.template.recruit.entity.Recruit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static goldstarproject.template.notice.entity.QNotice.notice;
import static goldstarproject.template.recruit.entity.QRecruit.*;

@RequiredArgsConstructor
public class RecruitRepositoryImpl implements RecruitRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<Recruit> findByRecruitContaining(String keyword, Pageable pageable) {
        QueryResults<Recruit> searchResults = queryFactory.selectFrom(recruit)
                .where(recruit.title.like("%" + keyword + "%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(searchResults.getResults(), pageable, searchResults.getTotal());
    }


    @Override
    public Page<Recruit> findByRecruitWriter(String username, Pageable pageable) {
        QueryResults<Recruit> searchResults = queryFactory.selectFrom(recruit)
                .where(recruit.writer.username.like("%" + username + "%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(searchResults.getResults(),pageable, searchResults.getTotal());
    }
}
