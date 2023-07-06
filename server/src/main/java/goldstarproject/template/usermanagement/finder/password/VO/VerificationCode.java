package goldstarproject.template.usermanagement.finder.password.VO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VerificationCode {

    private String code;
    private Long expirationTime;

}
