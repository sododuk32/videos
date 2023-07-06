package goldstarproject.template.community.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParentAndChildResponseDto {  //게시물의 댓글 & 대댓글만 조회 DTO/[리팩토링] : 네이밍
    private List<CommentResponseDto> commentResponseDtoList;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public ParentAndChildResponseDto(List<CommentResponseDto> commentResponseDtoList, Page<CommentResponseDto> page) {
        this.commentResponseDtoList = commentResponseDtoList;
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }
}