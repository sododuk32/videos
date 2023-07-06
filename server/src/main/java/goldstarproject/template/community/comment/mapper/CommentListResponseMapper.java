package goldstarproject.template.community.comment.mapper;


import goldstarproject.template.common.generic.GenericMapper;
import goldstarproject.template.community.comment.dto.ParentAndChildResponseDto;
import goldstarproject.template.community.comment.entity.Comment;
import goldstarproject.template.notice.dto.NoticeListDto;
import goldstarproject.template.notice.entity.Notice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentListResponseMapper extends GenericMapper<ParentAndChildResponseDto, Comment> {

    CommentListResponseMapper INSTANCE = Mappers.getMapper(CommentListResponseMapper.class);
}
