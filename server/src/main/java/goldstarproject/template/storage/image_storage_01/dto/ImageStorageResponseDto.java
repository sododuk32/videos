package goldstarproject.template.storage.image_storage_01.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import goldstarproject.template.storage.image_storage_01.entity.ImageStorage;
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
public class ImageStorageResponseDto {
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

    public ImageStorageResponseDto(ImageStorage image) {
        this.id = image.getId();
        this.title = image.getTitle();
        this.content = image.getContent();
        this.viewCount = image.getViewCount();
        this.likeCount = image.getLikeCount();
        this.commentCount = image.getCommentCount();
        this.createdAt = image.getCreatedAt();
        this.updatedAt = image.getUpdatedAt();
        this.writer = new MemberProfileDto(image.getWriter());
    }
}
