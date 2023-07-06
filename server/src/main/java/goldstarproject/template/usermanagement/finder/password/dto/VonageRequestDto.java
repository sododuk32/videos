package goldstarproject.template.usermanagement.finder.password.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class VonageRequestDto {
    private String phone;
    private String name;
    private String username;
    private String from;
    private String to;

}
