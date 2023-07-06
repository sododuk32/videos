package goldstarproject.template.notice.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import goldstarproject.template.community.board.entity.Board;
import goldstarproject.template.notice.dto.NoticeListDto;
import goldstarproject.template.notice.entity.Notice;
import goldstarproject.template.notice.entity.QNotice;
import goldstarproject.template.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static goldstarproject.template.community.board.entity.QBoard.board;
import static goldstarproject.template.notice.entity.QNotice.*;

@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void updateNoticeHeartCount(Notice notice, boolean b) {
        if (b) {
            queryFactory.update(QNotice.notice)
                    .set(QNotice.notice.likeCount, QNotice.notice.likeCount.add(1))
                    .where(QNotice.notice.eq(notice))
                    .execute();
        } else {
            queryFactory.update(QNotice.notice)
                    .set(QNotice.notice.likeCount, QNotice.notice.likeCount.subtract(1))
                    .where(QNotice.notice.eq(notice))
                    .execute();
        }
    }

    @Override
    public Page<Notice> findByNoticeContaining(String keyword, Pageable pageable) {
        QueryResults<Notice> searchResults = queryFactory.selectFrom(notice)
                .where(notice.title.like("%" + keyword + "%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(searchResults.getResults(), pageable, searchResults.getTotal());
    }


    @Override
    public Page<Notice> findByNoticeWriter(String username, Pageable pageable) {
        QueryResults<Notice> searchResults = queryFactory.selectFrom(notice)
                .where(notice.writer.username.like("%" + username + "%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(searchResults.getResults(),pageable, searchResults.getTotal());
    }

    @Override
    public Page<NoticeListDto> findSortedNoticesWithPagination(Pageable pageable) {
        QueryResults<Notice> results = queryFactory.selectFrom(notice)
                .orderBy(notice.isTop.desc(),notice.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<NoticeListDto> noticeListDtos = results .getResults().stream()
                .map(NoticeListDto::new)
                .collect(Collectors.toList());

        return new PageImpl<>(noticeListDtos, pageable, results.getTotal());
    }
}
