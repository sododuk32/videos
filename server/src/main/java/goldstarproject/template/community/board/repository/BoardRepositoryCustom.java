package goldstarproject.template.community.board.repository;


import goldstarproject.template.community.board.dto.BoardsListDto;
import goldstarproject.template.community.board.entity.Board;
import goldstarproject.template.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryCustom {
    void updateBoardHeartCount(Board board, boolean b);
    Page<Board> findByCategoryName(String category, Pageable pageable);
    Page<Board> findByBoardContaining(String keyword, Pageable pageable);
    Page<Board> findByBoardWriter(String username,Pageable pageable);
}
