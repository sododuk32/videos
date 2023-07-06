package goldstarproject.template.adconnect.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import goldstarproject.template.adconnect.entity.AdConnect;
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
public class AdConnectResponseDto {
    private Long id;
    private String title;
    private String content;
    private long commentCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;
    private MemberProfileDto writer;

    public AdConnectResponseDto(AdConnect adConnect) {
        this.id = adConnect.getId();
        this.title = adConnect.getTitle();
        this.content = adConnect.getContent();
        this.commentCount = adConnect.getCommentCount();
        this.createdAt = adConnect.getCreatedAt();
        this.updatedAt = adConnect.getUpdatedAt();
        this.writer = new MemberProfileDto(adConnect.getWriter());
    }
}
