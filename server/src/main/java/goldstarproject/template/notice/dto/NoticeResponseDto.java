package goldstarproject.template.notice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import goldstarproject.template.member.dto.MemberProfileDto;
import goldstarproject.template.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NoticeResponseDto {
    private Long id;
    private String title;
    private String content;
    private Boolean isTop;
    private int viewCount;
    private int likeCount;
    private long commentCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;
    private MemberProfileDto writer;

    public NoticeResponseDto(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.viewCount = notice.getViewCount();
        this.likeCount = notice.getLikeCount();
        this.commentCount = notice.getCommentCount();
        this.createdAt = notice.getCreatedAt();
        this.updatedAt = notice.getUpdatedAt();
        this.writer = new MemberProfileDto(notice.getWriter());
    }
}
