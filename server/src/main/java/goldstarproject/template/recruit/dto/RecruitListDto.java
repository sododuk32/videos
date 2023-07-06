package goldstarproject.template.recruit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import goldstarproject.template.member.dto.MemberProfileDto;
import goldstarproject.template.notice.entity.Notice;
import goldstarproject.template.recruit.entity.Recruit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class RecruitListDto {
    private Long id;
    private String title;
    private int likeCount;
    private long commentCount;
    private MemberProfileDto writer;  //회원 정보 가져오는 DTO



    public RecruitListDto(Recruit recruit) {
        this.id = recruit.getId();
        this.title = recruit.getTitle();
        this.commentCount = recruit.getCommentCount();
        this.likeCount = recruit.getLikeCount();
        this.writer = new MemberProfileDto(recruit.getWriter());
    }

    @Setter
    @Getter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public RecruitListDto() {
        this.createdAt = LocalDateTime.now();
    }
}
