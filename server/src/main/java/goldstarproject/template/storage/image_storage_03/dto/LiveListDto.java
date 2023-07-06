package goldstarproject.template.storage.image_storage_03.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import goldstarproject.template.member.dto.MemberProfileDto;
import goldstarproject.template.storage.image_storage_03.entity.Live;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class LiveListDto {
    private Long id;
    private String title;
    private String content;
    private String imageContent;
    private MemberProfileDto writer;  //회원 정보 가져오는 DTO


    public LiveListDto(Live live) {
        this.id = live.getId();
        this.title = live.getTitle();
        this.content = live.getContent();
        this.writer = new MemberProfileDto(live.getWriter());
    }

    @Setter
    @Getter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public LiveListDto() {
        this.createdAt = LocalDateTime.now();
    }
}
