package goldstarproject.template.notice.mapper;


import goldstarproject.template.common.generic.GenericMapper;
import goldstarproject.template.notice.dto.NoticeResponseDto;
import goldstarproject.template.notice.entity.Notice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NoticeResponseMapper extends GenericMapper<NoticeResponseDto, Notice> {
    NoticeResponseMapper INSTANCE = Mappers.getMapper(NoticeResponseMapper.class);
}

