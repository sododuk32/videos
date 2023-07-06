package goldstarproject.template.community.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import goldstarproject.template.community.board.entity.Board;
import goldstarproject.template.community.category.dto.CategoryRequestDto;
import goldstarproject.template.member.dto.MemberProfileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BoardsListDto {
    private Long id;
    private String title;
    private int viewCount;
    private int likeCount;
    private long commentCount;
    private MemberProfileDto writer;  //회원 정보 가져오는 DTO
    private CategoryRequestDto category;

    @Setter
    @Getter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public BoardsListDto() {
        this.createdAt = LocalDateTime.now();
    }


    public BoardsListDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.viewCount = board.getViewCount();
        this.likeCount = board.getLikeCount();
        this.commentCount = board.getCommentCount();
        this.writer = new MemberProfileDto(board.getWriter());
        this.category = new CategoryRequestDto(board.getCategory());
    }

}
