package goldstarproject.template.notice.mapper;


import goldstarproject.template.common.generic.GenericMapper;
import goldstarproject.template.notice.dto.NoticeListDto;
import goldstarproject.template.notice.entity.Notice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NoticeListResponseMapper extends GenericMapper<NoticeListDto, Notice> {

    NoticeListResponseMapper INSTANCE = Mappers.getMapper(NoticeListResponseMapper.class);
}
