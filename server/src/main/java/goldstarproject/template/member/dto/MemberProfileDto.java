package goldstarproject.template.member.dto;

import goldstarproject.template.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberProfileDto { //게시판에서 작성자를 가져올 때의 응답 DTO0
    private Long id;
    private String username;
    private String role; //추가여부 확인

    public MemberProfileDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.role = member.getRole();
    }


}
