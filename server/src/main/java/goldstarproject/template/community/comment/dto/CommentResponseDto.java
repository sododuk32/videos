package goldstarproject.template.community.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import goldstarproject.template.community.comment.entity.Comment;
import goldstarproject.template.member.dto.MemberProfileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String content;
    private MemberProfileDto writer;
    private List<CommentResponseDto> children = new ArrayList<>();
    @Getter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;



    public CommentResponseDto(Long id,String content,MemberProfileDto writer ) {
        this.id = id;
        this.content = content;
        this.writer = writer;
    }


    public static CommentResponseDto convertCommentToDto(Comment comment) {
        return comment.getIsDeleted() ?
                new CommentResponseDto(comment.getId(), "삭제된 댓글입니다.", null) :
                new CommentResponseDto(comment.getId(), comment.getContent(),
                        new MemberProfileDto(comment.getWriter()));
    }
}
