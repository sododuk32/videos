package goldstarproject.template.storage.image_storage_02.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import goldstarproject.template.storage.image_storage_02.entity.ImageUrlStorage;
import goldstarproject.template.member.dto.MemberProfileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageUrlStorageResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imageContent;
    private int viewCount;
    private int likeCount;
    private long commentCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;
    private MemberProfileDto writer;

    public ImageUrlStorageResponseDto(ImageUrlStorage images) {
        this.id = images.getId();
        this.title = images.getTitle();
        this.content = images.getContent();
        this.viewCount = images.getViewCount();
        this.likeCount = images.getLikeCount();
        this.commentCount = images.getCommentCount();
        this.createdAt = images.getCreatedAt();
        this.updatedAt = images.getUpdatedAt();
        this.writer = new MemberProfileDto(images.getWriter());
    }
}
