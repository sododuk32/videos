package goldstarproject.template.community.like.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class HeartRequestDto {

    private Long memberId;
    private Long boardId;
    private Long noticeId;
    private Long questionId;
    private Long imageId;
    private Long imagesId;


    @QueryProjection
    public HeartRequestDto(Long memberId, Long boardId, Long noticeId, Long questionId, Long imageId, Long imagesId) {
        this.memberId = memberId;
        this.boardId = boardId;
        this.noticeId = noticeId;
        this.questionId = questionId;
        this.imageId = imageId;
        this.imagesId = imagesId;
    }
}
