package goldstarproject.template.notice.service;

import goldstarproject.template.community.board.dto.BoardsListDto;
import org.springframework.data.domain.Page;

public interface NoticeService {
    void getViewCount(Long noticeId);
    Long getTotalCommentCount(Long noticeId);

}
