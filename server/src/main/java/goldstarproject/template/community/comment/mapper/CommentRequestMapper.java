package goldstarproject.template.community.comment.mapper;


import goldstarproject.template.common.generic.GenericMapper;
import goldstarproject.template.community.comment.dto.CommentRequestDto;
import goldstarproject.template.community.comment.entity.Comment;
import goldstarproject.template.notice.dto.NoticeRequestDto;
import goldstarproject.template.notice.entity.Notice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// 제네릭 매퍼를 상속받아 매핑해주는 역할
@Mapper(componentModel = "spring")
public interface CommentRequestMapper extends GenericMapper<CommentRequestDto, Comment> {
    CommentRequestMapper INSTANCE = Mappers.getMapper(CommentRequestMapper.class);
}
