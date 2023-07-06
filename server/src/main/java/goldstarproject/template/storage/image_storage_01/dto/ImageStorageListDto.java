package goldstarproject.template.storage.image_storage_01.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import goldstarproject.template.storage.image_storage_01.entity.ImageStorage;
import goldstarproject.template.member.dto.MemberProfileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ImageStorageListDto {
    private Long id;
    private String title;
    private String imageContent;
    private int likeCount;
    private int viewCount;
    private long commentCount;
    private MemberProfileDto writer;  //회원 정보 가져오는 DTO


    public ImageStorageListDto(ImageStorage image) {
        this.id = image.getId();
        this.title = image.getTitle();
        this.likeCount = image.getLikeCount();
        this.commentCount = image.getCommentCount();
        this.writer = new MemberProfileDto(image.getWriter());
    }

    @Setter
    @Getter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public ImageStorageListDto() {
        this.createdAt = LocalDateTime.now();
    }
}
