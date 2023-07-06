package goldstarproject.template.recruit.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import goldstarproject.template.member.dto.MemberProfileDto;
import goldstarproject.template.recruit.entity.Recruit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecruitResponseDto {
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
    private MemberProfileDto writer;


    public RecruitResponseDto(Recruit recruit)  {
        this.id = recruit.getId();
        this.title = recruit.getTitle();
        this.content = recruit.getContent();
        this.viewCount = recruit.getViewCount();
        this.likeCount = recruit.getLikeCount();
        this.commentCount = recruit.getCommentCount();
        this.createdAt = recruit.getCreatedAt();
        this.updatedAt = recruit.getUpdatedAt();
        this.writer = new MemberProfileDto(recruit.getWriter());
    }
}
