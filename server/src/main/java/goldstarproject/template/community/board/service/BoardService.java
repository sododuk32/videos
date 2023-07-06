package goldstarproject.template.community.board.service;

public interface BoardService {

    //게시판 공통 기능 추상화
    void getViewCount(Long boardId);
    Long getTotalCommentCount(Long boardId);

}
