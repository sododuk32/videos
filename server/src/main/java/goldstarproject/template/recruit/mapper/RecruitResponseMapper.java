package goldstarproject.template.recruit.mapper;


import goldstarproject.template.common.generic.GenericMapper;
import goldstarproject.template.recruit.dto.RecruitResponseDto;
import goldstarproject.template.recruit.entity.Recruit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RecruitResponseMapper extends GenericMapper<RecruitResponseDto, Recruit> {
    RecruitResponseMapper INSTANCE = Mappers.getMapper(RecruitResponseMapper.class);
}
