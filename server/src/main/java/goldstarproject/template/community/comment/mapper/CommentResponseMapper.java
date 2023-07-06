package goldstarproject.template.community.comment.mapper;


import goldstarproject.template.common.generic.GenericMapper;
import goldstarproject.template.community.comment.dto.CommentRequestDto;
import goldstarproject.template.community.comment.dto.CommentResponseDto;
import goldstarproject.template.community.comment.entity.Comment;
import goldstarproject.template.notice.dto.NoticeResponseDto;
import goldstarproject.template.notice.entity.Notice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentResponseMapper extends GenericMapper<CommentResponseDto, Comment> {
    CommentResponseMapper INSTANCE = Mappers.getMapper(CommentResponseMapper.class);
}

