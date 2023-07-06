package goldstarproject.template.question.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import goldstarproject.template.member.dto.MemberProfileDto;
import goldstarproject.template.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionResponseDto {
    private Long id;
    private String title;
    private String content;
    private boolean isSecret;
    private int viewCount;
    private int likeCount;
    private long commentCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;
    private MemberProfileDto writer;

    public QuestionResponseDto(Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.viewCount = question.getViewCount();
        this.likeCount = question.getLikeCount();
        this.commentCount = question.getCommentCount();
        this.createdAt = question.getCreatedAt();
        this.updatedAt = question.getUpdatedAt();
        this.writer = new MemberProfileDto(question.getWriter());
    }
}
