package goldstarproject.template.community.board.mapper;


import goldstarproject.template.common.generic.GenericMapper;
import goldstarproject.template.community.board.dto.BoardsListDto;
import goldstarproject.template.community.board.entity.Board;
import goldstarproject.template.notice.dto.NoticeListDto;
import goldstarproject.template.notice.entity.Notice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BoardListResponseMapper extends GenericMapper<BoardsListDto, Board> {

    BoardListResponseMapper INSTANCE = Mappers.getMapper(BoardListResponseMapper.class);
}
