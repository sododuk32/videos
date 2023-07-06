package goldstarproject.template.member.service;

import goldstarproject.template.common.exception.ExceptionCode;
import goldstarproject.template.common.exception.RestControllerException;
import goldstarproject.template.member.dto.MemberDto;
import goldstarproject.template.member.entity.Member;
import goldstarproject.template.member.mapper.MemberMapper;
import goldstarproject.template.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    /**
     * 특정 필드를 통해 값이 있을 경우 if 문을 통해 조건을 걸어주는 방법도 있을 수 있음
     */


    @Transactional  //회원 생성 메서드
    public MemberDto createMember(Member member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRole("ROLE_MEMBER");
        member.setCreatedAt(LocalDateTime.now());
        Member savedMember = memberRepository.save(member);
        return memberMapper.toMemberDto(savedMember);
    }

    @Transactional  //관리자 생성 메서드
    public MemberDto createAdmin(Member member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRole("ROLE_ADMIN");
        member.setCreatedAt(LocalDateTime.now());
        Member savedAdmin = memberRepository.save(member);
        return memberMapper.toMemberDto(savedAdmin);
    }

    @Transactional
    public MemberDto updateMember(Member member) {
        Member findMember = validateMember(member.getId());
        Optional.ofNullable(member.getPassword()).ifPresent(password -> findMember.setPassword(bCryptPasswordEncoder.encode(member.getPassword())));
        Optional.ofNullable(member.getUsername()).ifPresent(username -> findMember.setUsername(username));
        Optional.ofNullable(member.getPhone()).ifPresent(phone -> findMember.setPhone(phone));
        findMember.setUpdatedAt(LocalDateTime.now());
        Member savedMember = memberRepository.save(findMember);
        return memberMapper.toMemberDto(savedMember);
    }

    @Transactional
    public Member detailMember(Long memberId) {
        return validateMember(memberId);
    }

    @Transactional(readOnly = true)
    public Page<Member> findAllMember(int page, int size) {
        return memberRepository.findAll(PageRequest.of(
                page,size, Sort.by("id").descending()));
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = validateMember(memberId);
        memberRepository.delete(member);
    }

    public Member validateMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(()-> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }
}

