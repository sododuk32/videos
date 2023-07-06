package goldstarproject.template.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MemberUpdateDto {
    private Long id;
    @Pattern(regexp="([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9]){6,12}"
            ,message="숫자 영문자 특수 문자를 포함한 8 ~ 12 자를 입력하세요. ")
    private String password;
    @Pattern(regexp = "^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,16}$",
            message = " 2자 이상 16자 이하, 영어 또는 숫자 또는 한글로 입력하세요.")
    private String username;
    @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
            message = "휴대폰번호는 형식에 맞추어 11자리와 -를 포함시켜야합니다.")
    private String phone;
    private String name;
    @Getter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;
}
