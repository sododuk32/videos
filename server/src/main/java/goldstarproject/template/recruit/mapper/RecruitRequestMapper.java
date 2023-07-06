package goldstarproject.template.recruit.mapper;


import goldstarproject.template.common.generic.GenericMapper;
import goldstarproject.template.notice.mapper.NoticeRequestMapper;
import goldstarproject.template.recruit.dto.RecruitRequestDto;
import goldstarproject.template.recruit.entity.Recruit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RecruitRequestMapper extends GenericMapper<RecruitRequestDto, Recruit> {
    RecruitRequestMapper INSTANCE = Mappers.getMapper(RecruitRequestMapper.class);
}
