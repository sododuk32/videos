package goldstarproject.template.security.auth;

import goldstarproject.template.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@AllArgsConstructor  //PrincipalDetailService 에서 멤버를 가져올 떄 PrincipalDetails 에 멤버 생성자가 필요함
@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {

    private Member member;
    public Member getMember() {
        return member;
    }




    //권한 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        member.getRoleList().forEach(r -> {
            authorities.add(() -> {
                return r;
            });
        });
        return authorities;
    }


    //시큐리티 세션에 저장된 비밀번호
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    //시큐리티 세션에 저장된 닉네임
    @Override
    public String getUsername() {
        return member.getUsername();
    }




    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
