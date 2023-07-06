package goldstarproject.template.member.mapper;


import goldstarproject.template.member.dto.JoinDto;
import goldstarproject.template.member.dto.MemberDto;
import goldstarproject.template.member.dto.MemberUpdateDto;
import goldstarproject.template.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member toEntity(JoinDto joinDto);
    Member toEntity(MemberUpdateDto memberUpdateDto);
    MemberDto toMemberDto(Member member);
    JoinDto toJoinDto(Member member);
    List<MemberDto> toDtoList(List<Member> memberList);

}
