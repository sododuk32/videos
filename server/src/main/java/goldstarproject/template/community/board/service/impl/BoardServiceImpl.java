package goldstarproject.template.community.board.service.impl;

import goldstarproject.template.community.board.dto.BoardResponseDto;
import goldstarproject.template.community.board.dto.BoardUpdateRequestDto;
import goldstarproject.template.community.board.entity.Board;
import goldstarproject.template.community.board.mapper.BoardListResponseMapper;
import goldstarproject.template.community.board.mapper.BoardRequestMapper;
import goldstarproject.template.community.board.mapper.BoardResponseMapper;
import goldstarproject.template.community.board.repository.BoardRepository;
import goldstarproject.template.community.board.service.BoardService;
import goldstarproject.template.community.board.dto.BoardRequestDto;
import goldstarproject.template.community.board.dto.BoardsListDto;
import goldstarproject.template.common.exception.ExceptionCode;
import goldstarproject.template.common.exception.RestControllerException;
import goldstarproject.template.community.category.dto.CategoryRequestDto;
import goldstarproject.template.community.category.entity.Category;
import goldstarproject.template.community.category.repository.CategoryRepository;
import goldstarproject.template.community.comment.repository.CommentRepository;
import goldstarproject.template.member.dto.MemberProfileDto;
import goldstarproject.template.member.entity.Member;
import goldstarproject.template.member.repository.MemberRepository;
import goldstarproject.template.member.service.MemberService;
import goldstarproject.template.notice.entity.Notice;
import goldstarproject.template.question.dto.QuestionResponseDto;
import goldstarproject.template.question.entity.Question;
import goldstarproject.template.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final CategoryRepository categoryRepository;
    private final BoardRequestMapper boardRequestMapper;
    private final BoardResponseMapper boardResponseMapper;
    private final BoardListResponseMapper boardListResponseMapper;
    private final MemberService memberService;


    //게시물 작성
    @Transactional
    public BoardResponseDto insertBoard(Long categoryId, BoardRequestDto boardRequestDto, PrincipalDetails principalDetails) {
        Member member = memberRepository.findById(principalDetails.getMember().getId()).orElseThrow(() -> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RestControllerException(ExceptionCode.CATEGORY_NOT_FOUND));
        Board board = boardRequestMapper.toEntity(boardRequestDto);
        board.setWriter(member);
        board.setCategory(category);
        Board savedBoard = boardRepository.save(board);
        return boardResponseMapper.toDto(savedBoard);
    }


    //단건의 게시글만 조회 (댓글 미포함)
    @Transactional
    public BoardResponseDto detailBoard(Long boardId) {
        Board board = validateBoard(boardId);
        Long commentCount = getTotalCommentCount(board.getId());
        board.setCommentCount(commentCount.longValue());
        getViewCount(board.getId());
        return boardResponseMapper.toDto(board);
    }







    @Transactional
    public BoardResponseDto updateBoard(Long boardId, Long categoryId, BoardRequestDto boardRequestDto,PrincipalDetails principalDetails) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        Member member = memberRepository.findById(principalDetails.getMember().getId()).orElseThrow(() -> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RestControllerException(ExceptionCode.CATEGORY_NOT_FOUND));
        boardRequestMapper.toEntity(boardRequestDto);
        Optional.ofNullable(boardRequestDto.getTitle()).ifPresent(board::setTitle);
        Optional.ofNullable(boardRequestDto.getContent()).ifPresent(board::setContent);
        board.setWriter(member);
        getViewCount(boardId);
        getTotalCommentCount(boardId);
        board.setUpdatedAt(LocalDateTime.now());
        Board savedBoard = boardRepository.save(board);
        return boardResponseMapper.toDto(savedBoard);
    }


    //모든 게시물 조회 시 카테고리별 조회되어야함
    @Transactional(readOnly = true)
    public Page<BoardsListDto> findAllBoard(int page, int size) {
        Page<Board> boardPages = boardRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
        return boardPages.map(boardListResponseMapper::toDto);
    }


    //카테고리에 해당하는 게시물 가져오기
    @Transactional
    public Page<BoardsListDto> getBoardByCategory(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1,size,Sort.unsorted());

        Page<Board> boardPage = boardRepository.findByCategoryName(category,pageable);

        List<BoardsListDto> boardsList = boardPage.getContent().stream()
                .map(boardListResponseMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(boardsList, pageable,boardPage.getTotalElements());
    }


    //게시물 제목으로 검색
    public Page<BoardsListDto> searchList(String keyword,int page,int size) {
        Pageable pageable = PageRequest.of(page - 1,size,Sort.unsorted());
        Page<Board> boardPage = boardRepository.findByBoardContaining(keyword,pageable);
        List<BoardsListDto> boardsList = boardPage.getContent().stream()
                .map(boardListResponseMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(boardsList, pageable,boardPage.getTotalElements());
    }


    //게시물 작성자로 검색
    public Page<BoardsListDto> searchWriter(String writer,int page, int size) {
        Pageable pageable = PageRequest.of(page -1,size,Sort.unsorted());

        Page<Board> boardPage = boardRepository.findByBoardWriter(writer,pageable);

        List<BoardsListDto> boardsList = boardPage.getContent().stream()
                .map(boardListResponseMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(boardsList,pageable,boardPage.getTotalElements());
    }


    //게시글 삭제
    @Transactional
    public void deleteBoard(Long boardId,PrincipalDetails principalDetails) {
        Member member = memberService.detailMember(principalDetails.getMember().getId());
        Board deleteBoard = boardRepository.findById(boardId).orElseThrow(
                () -> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        boardRepository.delete(deleteBoard);
    }


    // 조회 수 합계
    @Transactional
    public void getViewCount(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        int totalViews = board.getViewCount();
        board.setViewCount(totalViews + 1);
        boardRepository.save(board);
    }


    //모든 댓글+대댓글 합계
    @Transactional
    public Long getTotalCommentCount(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        long totalCommentCount = commentRepository.getTotalCommentCountByBoardId(boardId).intValue();
        board.setCommentCount(totalCommentCount);
        boardRepository.save(board);
        return totalCommentCount;
    }



    //보드 식별자 찾기
    public Board validateBoard(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        Board findBoard = optionalBoard.orElseThrow(()-> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        return findBoard;
    }

}


