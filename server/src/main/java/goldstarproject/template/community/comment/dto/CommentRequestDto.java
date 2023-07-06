package goldstarproject.template.community.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    private Long memberId;
    private Long parentId;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    private int childrenCount;
}
