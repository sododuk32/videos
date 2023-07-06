package goldstarproject.template.usermanagement.join.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class VerifyResponseDto {
    private String message;
    @JsonInclude
    private String joinVerifyCode;
}
