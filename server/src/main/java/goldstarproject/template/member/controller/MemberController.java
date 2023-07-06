package goldstarproject.template.member.controller;

import goldstarproject.template.common.dto.MultiResponseDto;
import goldstarproject.template.common.dto.SingleResponseDto;
import goldstarproject.template.member.dto.JoinDto;
import goldstarproject.template.member.dto.MemberDto;
import goldstarproject.template.member.dto.MemberUpdateDto;
import goldstarproject.template.member.mapper.MemberMapper;
import goldstarproject.template.member.service.MemberService;
import goldstarproject.template.member.entity.Member;
import goldstarproject.template.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
@Validated
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper mapper;


    @PostMapping("/join")
    public ResponseEntity createMember(@Valid @RequestBody JoinDto joinDto) {
        MemberDto member = memberService.createMember(mapper.toEntity(joinDto));
        return new ResponseEntity<>(new SingleResponseDto<>(member), HttpStatus.CREATED);
    }


    @PostMapping("/manager/join")
    public ResponseEntity joinAdmin(@Valid @RequestBody JoinDto joinDto) {
        MemberDto response = memberService.createAdmin((mapper.toEntity(joinDto)));
        return new ResponseEntity<>(new SingleResponseDto<>((response)), HttpStatus.CREATED);
    }


    @PatchMapping("/{member-id}/update")
    public ResponseEntity updateMember(@PathVariable("member-id")  @Positive Long memberId,
                                       @RequestBody MemberUpdateDto memberUpdateDto) {
        memberUpdateDto.setId(memberId);
        MemberDto member = memberService.updateMember(mapper.toEntity(memberUpdateDto));
        return new ResponseEntity<>(new SingleResponseDto<>((member)),HttpStatus.OK);
    }


    @GetMapping("/admin/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive Long memberId,Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Member member = memberService.detailMember(memberId);
        return new ResponseEntity<>(new SingleResponseDto<>(mapper.toMemberDto(member)),HttpStatus.OK);
    }


    @GetMapping("admin/members")
    public ResponseEntity getMembers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {
        Page<Member> pages = memberService.findAllMember(page -1, size);
        List<Member> membersList = pages.getContent();
        return new ResponseEntity<>(new MultiResponseDto<>(mapper.toDtoList(membersList),pages),HttpStatus.OK);
    }


    @DeleteMapping("/member/{member-id}/delete")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}

