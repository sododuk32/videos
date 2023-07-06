package goldstarproject.template.community.board.mapper;


import goldstarproject.template.common.generic.GenericMapper;
import goldstarproject.template.community.board.dto.BoardResponseDto;
import goldstarproject.template.community.board.entity.Board;
import goldstarproject.template.notice.dto.NoticeResponseDto;
import goldstarproject.template.notice.entity.Notice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BoardResponseMapper extends GenericMapper<BoardResponseDto, Board> {
    BoardResponseMapper INSTANCE = Mappers.getMapper(BoardResponseMapper.class);
}

