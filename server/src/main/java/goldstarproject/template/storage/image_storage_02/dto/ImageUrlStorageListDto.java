package goldstarproject.template.storage.image_storage_02.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import goldstarproject.template.storage.image_storage_02.entity.ImageUrlStorage;
import goldstarproject.template.member.dto.MemberProfileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ImageUrlStorageListDto {
    private Long id;
    private String title;
    private String imageContent;
    private int likeCount;
    private int viewCount;
    private long commentCount;
    private MemberProfileDto writer;  //회원 정보 가져오는 DTO


    public ImageUrlStorageListDto(ImageUrlStorage imageUrl) {
        this.id = imageUrl.getId();
        this.title = imageUrl.getTitle();
        this.commentCount = imageUrl.getCommentCount();
        this.likeCount = imageUrl.getLikeCount();
        this.writer = new MemberProfileDto(imageUrl.getWriter());
    }

    @Setter
    @Getter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public ImageUrlStorageListDto() {
        this.createdAt = LocalDateTime.now();
    }
}
