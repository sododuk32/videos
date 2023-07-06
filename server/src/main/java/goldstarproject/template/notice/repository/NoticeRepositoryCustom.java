package goldstarproject.template.notice.repository;

import goldstarproject.template.community.board.entity.Board;
import goldstarproject.template.notice.dto.NoticeListDto;
import goldstarproject.template.notice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustom {
    void updateNoticeHeartCount(Notice notice, boolean b);
    Page<Notice> findByNoticeContaining(String keyword, Pageable pageable);
    Page<Notice> findByNoticeWriter(String username,Pageable pageable);
    Page<NoticeListDto> findSortedNoticesWithPagination(Pageable pageable);



}
