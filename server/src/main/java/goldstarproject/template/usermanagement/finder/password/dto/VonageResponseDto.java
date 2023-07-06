package goldstarproject.template.usermanagement.finder.password.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class VonageResponseDto {
    private String message;
    private String VerificationCode;
    private String newPassword;

}
