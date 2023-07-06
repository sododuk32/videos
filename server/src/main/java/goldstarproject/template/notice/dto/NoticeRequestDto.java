package goldstarproject.template.notice.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NoticeRequestDto {
    private Long id;
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    private Boolean isTop;
    private int viewCount;
    private int likeCount;
    private long commentCount;
}
