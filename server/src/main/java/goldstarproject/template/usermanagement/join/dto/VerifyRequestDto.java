package goldstarproject.template.usermanagement.join.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class VerifyRequestDto {
    private String from;
    private String to;
}
