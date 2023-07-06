package goldstarproject.template.adconnect.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import goldstarproject.template.adconnect.entity.AdConnect;
import goldstarproject.template.member.dto.MemberProfileDto;
import goldstarproject.template.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
@Setter
public class AdConnectListDto {
    private Long id;
    private String title;
    private long commentCount;
    private MemberProfileDto writer;  //회원 정보 가져오는 DTO

    public AdConnectListDto(AdConnect adConnect) {
        this.id = adConnect.getId();
        this.title = adConnect.getTitle();
        this.commentCount = adConnect.getCommentCount();
        this.writer = new MemberProfileDto(adConnect.getWriter());
    }

    @Setter
    @Getter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public AdConnectListDto() {
        this.createdAt = LocalDateTime.now();
    }

}
