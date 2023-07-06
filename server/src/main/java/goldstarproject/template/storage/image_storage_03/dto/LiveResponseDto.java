package goldstarproject.template.storage.image_storage_03.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import goldstarproject.template.member.dto.MemberProfileDto;

import goldstarproject.template.storage.image_storage_03.entity.Live;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LiveResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imageContent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;
    private MemberProfileDto writer;

    public LiveResponseDto(Live live) {
        this.id = live.getId();
        this.title = live.getTitle();
        this.content = live.getContent();
        this.createdAt = live.getCreatedAt();
        this.updatedAt = live.getUpdatedAt();
        this.writer = new MemberProfileDto(live.getWriter());
    }
}
