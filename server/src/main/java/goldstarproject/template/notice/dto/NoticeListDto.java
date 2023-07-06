package goldstarproject.template.notice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import goldstarproject.template.member.dto.MemberProfileDto;
import goldstarproject.template.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class NoticeListDto {
    private Long id;
    private String title;
    private Boolean isTop;
    private int likeCount;
    private int viewCount;
    private long commentCount;
    private MemberProfileDto writer;  //회원 정보 가져오는 DTO


    public NoticeListDto(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.likeCount = notice.getLikeCount();
        this.commentCount = notice.getCommentCount();
        this.isTop = notice.getIsTop();
        this.createdAt = notice.getCreatedAt();
        this.writer = new MemberProfileDto(notice.getWriter());
    }

    @Setter
    @Getter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public NoticeListDto() {
        this.createdAt = LocalDateTime.now();
    }
}
