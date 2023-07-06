package goldstarproject.template.community.board.mapper;


import goldstarproject.template.common.generic.GenericMapper;
import goldstarproject.template.community.board.dto.BoardRequestDto;
import goldstarproject.template.community.board.entity.Board;
import goldstarproject.template.notice.dto.NoticeRequestDto;
import goldstarproject.template.notice.entity.Notice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// 제네릭 매퍼를 상속받아 매핑해주는 역할
@Mapper(componentModel = "spring")
public interface BoardRequestMapper extends GenericMapper<BoardRequestDto, Board> {
    BoardRequestMapper INSTANCE = Mappers.getMapper(BoardRequestMapper.class);
}
