package goldstarproject.template.member.entity;


import goldstarproject.template.common.BaseEntity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "members")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    private String name;
    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false,unique = true)
    private String phone;

    // 권한리턴에 필요한 role 컬럼
    @Column(nullable = false)
    private String role;


    public List<String> getRoleList() {
        if (this.role.length() > 0) {
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }

    public boolean isAdmin(Member member) {
        List<String> roles = member.getRoleList();
        return roles.contains("ADMIN");
    }


}
