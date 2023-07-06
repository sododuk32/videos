package goldstarproject.template.community.board.controller;


import goldstarproject.template.community.board.dto.BoardResponseDto;
import goldstarproject.template.community.board.dto.BoardUpdateRequestDto;
import goldstarproject.template.community.board.dto.BoardRequestDto;
import goldstarproject.template.common.dto.MultiResponseDto;
import goldstarproject.template.common.dto.SingleResponseDto;
import goldstarproject.template.community.board.dto.BoardsListDto;
import goldstarproject.template.community.board.service.impl.BoardServiceImpl;
import goldstarproject.template.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;


@Validated
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boardService;



    // 게시판 -> 게시물 조회
    @GetMapping("/board/{board-id}")
    public ResponseEntity getBoard(@PathVariable ("board-id") @Positive Long boardId) {
            BoardResponseDto response = boardService.detailBoard(boardId);
            return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.OK);
    }


    @PostMapping("/category/{category-id}/{member-id}/board/insert")
    public ResponseEntity insertBoard(@PathVariable ("category-id") @Positive Long categoryId,
                                      @PathVariable ("member-id") @Positive Long memberId,
                                      @Valid @RequestBody BoardRequestDto boardRequestDto,
                                      Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        BoardResponseDto response = boardService.insertBoard(categoryId,boardRequestDto,principalDetails);
        return new ResponseEntity(new SingleResponseDto<>((response)),HttpStatus.CREATED);
    }


    @PatchMapping("/category/{category-id}/board/{board-id}/member/{member-id}/update")
    public ResponseEntity updateBoard(@PathVariable("board-id") @Positive Long boardId,
                                      @PathVariable("category-id") @Positive Long categoryId,
                                      @PathVariable("member-id") @Positive Long memberId,
                                      @RequestParam(required = false) String password,
                                      @Valid @RequestBody BoardRequestDto boardRequestDto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        BoardResponseDto response = boardService.updateBoard(boardId,categoryId,boardRequestDto,principalDetails);
        return new ResponseEntity<>(new SingleResponseDto<>((response)), HttpStatus.OK);
    }


    //정렬 순서 최신 글이 상단으로 오도록 정렬 순서를 DESC 바꿔준다
    //카테고리 별 게시물 조회 /category/{카테고리 이름}
    // ToDo pageNumber + 1 시켜야함
    @GetMapping("/category/{category}")
    public ResponseEntity<Page<BoardsListDto>> getBoardByCategory(@PathVariable String category,
                                                                  @RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(defaultValue = "0") int size) {
        Page<BoardsListDto> pages = boardService.getBoardByCategory(category,page,size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(),pages),HttpStatus.OK);
    }


    // 게시물 목록 조회 (기본)
    @GetMapping("/boards")
    public ResponseEntity boardsDesc(@RequestParam @Positive int page,
                                     @RequestParam @Positive int size) {
        Page<BoardsListDto> pages = boardService.findAllBoard(page - 1, size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(),pages),HttpStatus.OK);
    }


    //게시물 제목으로 검색
    @GetMapping("/search/board")
    public ResponseEntity<Page<BoardsListDto>> searchBoardTitle(@RequestParam("keyword") String keyword,
                                                                @RequestParam("page") int page,
                                                                @RequestParam("size") int size) {
        Page<BoardsListDto> pages = boardService.searchList(keyword,page,size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(),pages),HttpStatus.OK);
    }

    //작성자 제목으로 검색
    @GetMapping("/search/board/username")
    public ResponseEntity<Page<BoardsListDto>> searchBoardWriter(@RequestParam("username") String username,
                                                                 @RequestParam("page") int page,
                                                                 @RequestParam("size") int size) {
        Page<BoardsListDto> pages = boardService.searchWriter(username,page,size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(),pages),HttpStatus.OK);
    }

    @DeleteMapping("member/board/{board-id}/delete")
    public ResponseEntity deleteBoard(@PathVariable("board-id") @Positive Long boardId,
                                      Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        boardService.deleteBoard(boardId,principalDetails);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
