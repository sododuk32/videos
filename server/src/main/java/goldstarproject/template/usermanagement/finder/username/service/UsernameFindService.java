package goldstarproject.template.usermanagement.finder.username.service;

import goldstarproject.template.member.entity.Member;
import goldstarproject.template.member.repository.MemberRepository;
import goldstarproject.template.member.service.MemberService;
import goldstarproject.template.usermanagement.finder.username.dto.UsernameDto;
import goldstarproject.template.usermanagement.finder.username.dto.UsernameFindDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsernameFindService {

    private final MemberService memberService;
    private final MemberRepository memberRepository;


    //회원의 실명과 휴대폰 번호가 저장된 내용과 동일하면 해당 유저의 username 을 설정
    public String findUsername(UsernameFindDto findMemberProfileDto) {
        Member member = memberRepository.findByNameAndPhone(findMemberProfileDto.getName(),findMemberProfileDto.getPhone());
        if (member != null) {
            return member.getUsername();
        }
        return null;
    }


    //회원 유저네임 찾기
    public boolean isUsernameDuplicate(String username) {
        if (username.isBlank()) {
            throw new IllegalArgumentException("유저네임을 입력하세요");
        }
        return memberRepository.existsByUsername(username);
    }
}
