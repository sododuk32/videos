package goldstarproject.template.question.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import goldstarproject.template.question.entity.Question;
import goldstarproject.template.member.dto.MemberProfileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
@Setter
public class QuestionListDto  {
    private Long id;
    private String title;
    private int likeCount;
    private int viewCount;
    private boolean isSecret;
    private long commentCount;
    private MemberProfileDto writer;  //회원 정보 가져오는 DTO

    public QuestionListDto(Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.viewCount = question.getViewCount();
        this.likeCount = question.getLikeCount();
        this.commentCount = question.getCommentCount();
        this.writer = new MemberProfileDto(question.getWriter());
    }

    @Setter
    @Getter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public QuestionListDto() {
        this.createdAt = LocalDateTime.now();
    }
}
