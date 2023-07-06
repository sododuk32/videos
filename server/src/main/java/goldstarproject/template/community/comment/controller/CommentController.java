package goldstarproject.template.community.comment.controller;

import goldstarproject.template.common.dto.MultiResponseDto;
import goldstarproject.template.community.board.entity.BoardType;
import goldstarproject.template.community.comment.dto.CommentRequestDto;
import goldstarproject.template.community.comment.dto.CommentResponseDto;
import goldstarproject.template.community.comment.dto.ParentAndChildResponseDto;
import goldstarproject.template.common.dto.SingleResponseDto;
import goldstarproject.template.community.comment.service.impl.CommentServiceImpl;
import goldstarproject.template.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;


@Validated
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping("/member/{board-type}/{board-id}/comment")
    public ResponseEntity insertComment(@PathVariable("board-type") String boardType,
                                                 @PathVariable("board-id") @Positive Long boardId,
                                                 @Valid @RequestBody CommentRequestDto commentRequestDto,
                                        Authentication authentication) {
        CommentResponseDto response;
        if (boardType.equals("recruit")) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            response = commentService.insertRecruitComment(boardId, commentRequestDto,principalDetails);
        } else if (boardType.equals("notice")) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            response = commentService.insertNoticeComment(boardId, commentRequestDto,principalDetails);
        } else if (boardType.equals("board")) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            response = commentService.insertComment(boardId, commentRequestDto,principalDetails);
        } else if (boardType.equals("question")) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            response = commentService.insertQuestionComment(boardId, commentRequestDto,principalDetails);
        } else if (boardType.equals("image")) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            response = commentService.insertImageComment(boardId,commentRequestDto,principalDetails);
        } else if (boardType.equals("images")) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            response = commentService.insertImageUrlComment(boardId,commentRequestDto,principalDetails);
        } else if (boardType.equals("adConnects")) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            response = commentService.insertAdConnectComment(boardId, commentRequestDto, principalDetails);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new SingleResponseDto<>((response)), HttpStatus.CREATED);
    }




    @GetMapping("/{board-type}/{boardId}/comments/get")
    public ResponseEntity<MultiResponseDto<CommentResponseDto>> getCommentsByBoardId(@PathVariable("board-type") BoardType boardType,
                                                                                     @PathVariable("boardId") @Positive Long boardId,
                                                                                     @RequestParam(defaultValue = "1") int page,
                                                                                     @RequestParam(defaultValue = "10") int size){
        if (boardType == BoardType.BOARD) {
            Page<CommentResponseDto> commentPage = commentService.getCommentsByBoardId(boardId, page - 1, size);
            MultiResponseDto<CommentResponseDto> responseDto = new MultiResponseDto<>(commentPage.getContent(), commentPage);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else if (boardType == BoardType.RECRUIT) {
            Page<CommentResponseDto> commentPage = commentService.findByRecruitParentAndChild(boardId, page - 1, size);
            MultiResponseDto<CommentResponseDto> responseDto = new MultiResponseDto<>(commentPage.getContent(), commentPage);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else if (boardType == BoardType.NOTICE) {
            Page<CommentResponseDto> commentPage = commentService.findByNoticeParentAndChild(boardId, page - 1, size);
            MultiResponseDto<CommentResponseDto> responseDto = new MultiResponseDto<>(commentPage.getContent(), commentPage);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else if (boardType == BoardType.QUESTION) {
            Page<CommentResponseDto> commentPage = commentService.findByQuestionParentAndChild(boardId, page - 1, size);
            MultiResponseDto<CommentResponseDto> responseDto = new MultiResponseDto<>(commentPage.getContent(), commentPage);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else if (boardType == BoardType.IMAGE) {
            Page<CommentResponseDto> commentPage = commentService.findByImageParentAndChild(boardId, page - 1, size);
            MultiResponseDto<CommentResponseDto> responseDto = new MultiResponseDto<>(commentPage.getContent(), commentPage);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else if (boardType == BoardType.IMAGES) {
            Page<CommentResponseDto> commentPage = commentService.findByImageUrlParentAndChild(boardId, page - 1, size);
            MultiResponseDto<CommentResponseDto> responseDto = new MultiResponseDto<>(commentPage.getContent(), commentPage);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else if (boardType == BoardType.ADCONNECTS) {
            Page<CommentResponseDto> commentPage = commentService.findByAdConnectParentAndChild(boardId, page - 1, size);
            MultiResponseDto<CommentResponseDto> responseDto = new MultiResponseDto<>(commentPage.getContent(), commentPage);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @DeleteMapping("/member/{board-type}/{board-id}/delete/{comment-id}")
    public ResponseEntity deleteComment(@PathVariable("board-type") String boardType,
                                        @PathVariable("board-id") @Positive Long boardId,
                                        @PathVariable("comment-id") @Positive Long commentId,
                                        Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        if (boardType.equals("board")) {
            commentService.deleteComment(commentId, principalDetails);
            return ResponseEntity.ok("Board Comment deleted successfully");
        } else if (boardType.equals("recruit")) {
            commentService.deleteRecruitComment( commentId, principalDetails);
            return ResponseEntity.ok("Recruit comment deleted successfully");
        } else if (boardType.equals("question")) {
            commentService.deleteQuestionComment( commentId, principalDetails);
            return ResponseEntity.ok("Question comment deleted successfully");
        } else if (boardType.equals("notice")) {
            commentService.deleteNoticeComment(commentId, principalDetails);
            return ResponseEntity.ok("Notice comment deleted successfully");
        } else if (boardType.equals("image")) {
            commentService.deleteImageComment(commentId, principalDetails);
            return ResponseEntity.ok("Image comment deleted successfully");
        } else if (boardType.equals("images")) {
            commentService.deleteImageUrlComment( commentId, principalDetails);
            return ResponseEntity.ok("Images comment deleted successfully");
        } else if (boardType.equals("adConnects")) {
            commentService.deleteAdConnectComment( commentId, principalDetails);
            return ResponseEntity.ok("adConnects comment deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid comment type");
        }
    }
}