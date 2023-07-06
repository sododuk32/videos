package goldstarproject.template.community.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import goldstarproject.template.community.board.entity.Board;
import goldstarproject.template.community.category.dto.CategoryRequestDto;
import goldstarproject.template.community.comment.dto.CommentResponseDto;
import goldstarproject.template.member.dto.MemberProfileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private int viewCount;
    private int likeCount;
    private long commentCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;
    private MemberProfileDto writer;  //회원 정보 가져오는 DTO
    private CategoryRequestDto category;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.viewCount = board.getViewCount();
        this.likeCount = board.getLikeCount();
        this.commentCount = board.getCommentCount();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
        this.writer = new MemberProfileDto(board.getWriter());
        this.category = new CategoryRequestDto(board.getCategory());
    }
}

