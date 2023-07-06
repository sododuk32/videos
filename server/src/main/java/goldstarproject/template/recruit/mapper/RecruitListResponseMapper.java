package goldstarproject.template.recruit.mapper;


import goldstarproject.template.common.generic.GenericMapper;
import goldstarproject.template.notice.mapper.NoticeListResponseMapper;
import goldstarproject.template.recruit.dto.RecruitListDto;
import goldstarproject.template.recruit.entity.Recruit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RecruitListResponseMapper extends GenericMapper<RecruitListDto, Recruit> {

    RecruitListResponseMapper INSTANCE = Mappers.getMapper(RecruitListResponseMapper.class);

}
