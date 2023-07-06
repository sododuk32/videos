package goldstarproject.template.usermanagement.finder.username.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UsernameDto {
    private String Username;
    public UsernameDto(String username) {
        Username = username;
    }
}
