package goldstarproject.template.community.comment.service.impl;

import goldstarproject.template.adconnect.entity.AdConnect;
import goldstarproject.template.adconnect.service.AdConnectService;
import goldstarproject.template.community.board.entity.Board;
import goldstarproject.template.community.board.repository.BoardRepository;
import goldstarproject.template.community.board.service.impl.BoardServiceImpl;
import goldstarproject.template.community.comment.dto.CommentRequestDto;
import goldstarproject.template.community.comment.dto.CommentResponseDto;
import goldstarproject.template.community.comment.entity.Comment;
import goldstarproject.template.community.comment.mapper.CommentListResponseMapper;
import goldstarproject.template.community.comment.mapper.CommentRequestMapper;
import goldstarproject.template.community.comment.mapper.CommentResponseMapper;
import goldstarproject.template.community.comment.repository.CommentRepository;
import goldstarproject.template.common.exception.ExceptionCode;
import goldstarproject.template.common.exception.RestControllerException;
import goldstarproject.template.question.entity.Question;
import goldstarproject.template.question.service.impl.QuestionServiceImpl;
import goldstarproject.template.member.entity.Member;
import goldstarproject.template.member.repository.MemberRepository;
import goldstarproject.template.notice.entity.Notice;
import goldstarproject.template.notice.service.impl.NoticeServiceImpl;
import goldstarproject.template.recruit.entity.Recruit;
import goldstarproject.template.recruit.service.impl.RecruitServiceImpl;
import goldstarproject.template.security.auth.PrincipalDetails;
import goldstarproject.template.storage.image_storage_01.entity.ImageStorage;
import goldstarproject.template.storage.image_storage_01.service.impl.ImageStorageServiceImpl;
import goldstarproject.template.storage.image_storage_02.entity.ImageUrlStorage;
import goldstarproject.template.storage.image_storage_02.service.impl.ImageUrlStorageServiceImpl;
import goldstarproject.template.storage.image_storage_03.entity.Live;
import goldstarproject.template.storage.image_storage_03.service.impl.LiveServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl {


    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BoardServiceImpl boardService;
    private final NoticeServiceImpl noticeService;
    private final RecruitServiceImpl recruitService;
    private final QuestionServiceImpl questionService;
    private final ImageStorageServiceImpl imageStorageService;
    private final ImageUrlStorageServiceImpl imageUrlStorageService;
    private final CommentRequestMapper commentRequestMapper;
    private final CommentResponseMapper commentResponseMapper;
    private final CommentListResponseMapper commentListResponseMapper;
    private final AdConnectService adConnectService;
    private final LiveServiceImpl liveService;


    //댓글 & 대댓글 생성
    @Transactional
    public CommentResponseDto insertComment(Long boardId, CommentRequestDto commentRequestDto, PrincipalDetails principalDetails) {
        Member member = memberRepository.findById(principalDetails.getMember().getId()).orElseThrow(() -> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        Board board = boardService.validateBoard(boardId);
        Comment comment = commentRequestMapper.toEntity(commentRequestDto);
        Comment parentComment;
        if (commentRequestDto.getParentId() != null) {
            parentComment = commentRepository.findByIdAndBoard(commentRequestDto.getParentId(),board).orElseThrow(
                    () -> new RestControllerException(ExceptionCode.PARENT_NOT_FOUND));
            comment.updateParent(parentComment);
        }
        comment.updateWriter(member);
        comment.setBoard(board);
        comment.setCreatedAt(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        return commentResponseMapper.toDto(savedComment);
    }

    @Transactional
    public CommentResponseDto insertNoticeComment(Long noticeId, CommentRequestDto commentRequestDto, PrincipalDetails principalDetails) {
        Member member = memberRepository.findById(principalDetails.getMember().getId()).orElseThrow(() -> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        Notice notice = noticeService.validateNotice(noticeId);
        Comment comment = commentRequestMapper.toEntity(commentRequestDto);
        Comment parentComment;
        if (commentRequestDto.getParentId() != null) {
            parentComment = commentRepository.findByIdAndNotice(commentRequestDto.getParentId(),notice).orElseThrow(
                    () -> new RestControllerException(ExceptionCode.PARENT_NOT_FOUND));
            comment.updateParent(parentComment);
        }
        comment.updateWriter(member);
        comment.setNotice(notice);
        comment.setCreatedAt(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        return commentResponseMapper.toDto(savedComment);
    }

    @Transactional
    public CommentResponseDto insertRecruitComment(Long recruitId, CommentRequestDto commentRequestDto, PrincipalDetails principalDetails) {
        Member member = memberRepository.findById(principalDetails.getMember().getId()).orElseThrow(() -> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        Recruit recruit = recruitService.validateRecruit(recruitId);
        Comment comment = commentRequestMapper.toEntity(commentRequestDto);
        Comment parentComment;
        if (commentRequestDto.getParentId() != null) {
            parentComment = commentRepository.findByIdAndRecruit(commentRequestDto.getParentId(),recruit).orElseThrow(
                    () -> new RestControllerException(ExceptionCode.PARENT_NOT_FOUND));
            comment.updateParent(parentComment);
        }
        comment.updateWriter(member);
        comment.setRecruit(recruit);
        comment.setCreatedAt(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        return commentResponseMapper.toDto(savedComment);
    }

    @Transactional
    public CommentResponseDto insertQuestionComment(Long questionId, CommentRequestDto commentRequestDto, PrincipalDetails principalDetails) {
        Member member = memberRepository.findById(principalDetails.getMember().getId()).orElseThrow(() -> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        Question question = questionService.validateQuestion(questionId);
        Comment comment = commentRequestMapper.toEntity(commentRequestDto);
        Comment parentComment;
        if (commentRequestDto.getParentId() != null) {
            parentComment = commentRepository.findByIdAndQuestion(commentRequestDto.getParentId(),question).orElseThrow(
                    () -> new RestControllerException(ExceptionCode.PARENT_NOT_FOUND));
            comment.updateParent(parentComment);
        }
        comment.updateWriter(member);
        comment.setQuestion(question);
        comment.setCreatedAt(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        return commentResponseMapper.toDto(savedComment);
    }

    @Transactional
    public CommentResponseDto insertImageComment(Long imageId, CommentRequestDto commentRequestDto, PrincipalDetails principalDetails) {
        Member member = memberRepository.findById(principalDetails.getMember().getId()).orElseThrow(() -> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        ImageStorage image = imageStorageService.validateImage(imageId);
        Comment comment = commentRequestMapper.toEntity(commentRequestDto);
        Comment parentComment;
        if (commentRequestDto.getParentId() != null) {
            parentComment = commentRepository.findByIdAndImage(commentRequestDto.getParentId(),image).orElseThrow(
                    () -> new RestControllerException(ExceptionCode.PARENT_NOT_FOUND));
            comment.updateParent(parentComment);
        }
        comment.updateWriter(member);
        comment.setImage(image);
        comment.setCreatedAt(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        return commentResponseMapper.toDto(savedComment);
    }

    @Transactional
    public CommentResponseDto insertImageUrlComment(Long imagesId, CommentRequestDto commentRequestDto, PrincipalDetails principalDetails) {
        Member member = memberRepository.findById(principalDetails.getMember().getId()).orElseThrow(() -> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        ImageUrlStorage images = imageUrlStorageService.validateImageUrl(imagesId);
        Comment comment = commentRequestMapper.toEntity(commentRequestDto);
        Comment parentComment;
        if (commentRequestDto.getParentId() != null) {
            parentComment = commentRepository.findByIdAndImages(commentRequestDto.getParentId(),images).orElseThrow(
                    () -> new RestControllerException(ExceptionCode.PARENT_NOT_FOUND));
            comment.updateParent(parentComment);
        }
        comment.updateWriter(member);
        comment.setImages(images);
        comment.setCreatedAt(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        return commentResponseMapper.toDto(savedComment);
    }


    @Transactional
    public CommentResponseDto insertAdConnectComment(Long adConnectId, CommentRequestDto commentRequestDto, PrincipalDetails principalDetails) {
        Member member = memberRepository.findById(principalDetails.getMember().getId()).orElseThrow(() -> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        AdConnect adConnect = adConnectService.validateAdConnect(adConnectId);
        Comment comment = commentRequestMapper.toEntity(commentRequestDto);
        Comment parentComment;
        if (commentRequestDto.getParentId() != null) {
            parentComment = commentRepository.findByIdAndAdConnect(commentRequestDto.getParentId(),adConnect).orElseThrow(
                    () -> new RestControllerException(ExceptionCode.PARENT_NOT_FOUND));
            comment.updateParent(parentComment);
        }
        comment.updateWriter(member);
        comment.setAdConnect(adConnect);
        comment.setCreatedAt(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        return commentResponseMapper.toDto(savedComment);
    }





    //리팩토링 조회라인
    @Transactional(readOnly = true)
    public Page<CommentResponseDto> getCommentsByBoardId(Long boardId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findByBoardParentAndChild(boardId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<CommentResponseDto> findByNoticeParentAndChild(Long boardId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findByNoticeParentAndChild(boardId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<CommentResponseDto> findByRecruitParentAndChild(Long boardId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findByRecruitParentAndChild(boardId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<CommentResponseDto> findByQuestionParentAndChild(Long boardId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findByQuestionParentAndChild(boardId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<CommentResponseDto> findByImageParentAndChild(Long boardId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findByImageParentAndChild(boardId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<CommentResponseDto> findByImageUrlParentAndChild(Long boardId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findByImagesUrlParentAndChild(boardId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<CommentResponseDto> findByAdConnectParentAndChild(Long boardId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findByAdConnectsParentAndChild(boardId, pageable);
    }






    //삭제 라인
    @Transactional
    public void deleteComment(Long commentId,PrincipalDetails principalDetails) {
        Comment comment = commentRepository.findBoardCommentByIdWithParent(commentId)
                .orElseThrow(() -> new RestControllerException(ExceptionCode.COMMENT_NOT_FOUND));
        if(comment.getChildren().size() != 0) { // 자식이 있으면 상태만 변경
            comment.changeIsDeleted(true);
        } else { // 삭제 가능한 조상 댓글을 구해서 삭제
            commentRepository.delete(getDeletableAncestorComment(comment));
        }
    }

    @Transactional
    public void deleteRecruitComment(Long commentId,PrincipalDetails principalDetails) {
        Comment comment = commentRepository.findRecruitCommentByWithParent(commentId)
                .orElseThrow(() -> new RestControllerException(ExceptionCode.COMMENT_NOT_FOUND));
        if(comment.getChildren().size() != 0) { // 자식이 있으면 상태만 변경
            comment.changeIsDeleted(true);
        } else { // 삭제 가능한 조상 댓글을 구해서 삭제
            commentRepository.delete(getDeletableAncestorComment(comment));
        }
    }

    @Transactional
    public void deleteQuestionComment(Long commentId,PrincipalDetails principalDetails) {
        Comment comment = commentRepository.findQuestionCommentByWithParent(commentId)
                .orElseThrow(() -> new RestControllerException(ExceptionCode.COMMENT_NOT_FOUND));
        if(comment.getChildren().size() != 0) { // 자식이 있으면 상태만 변경
            comment.changeIsDeleted(true);
        } else { // 삭제 가능한 조상 댓글을 구해서 삭제
            commentRepository.delete(getDeletableAncestorComment(comment));
        }
    }

    @Transactional
    public void deleteNoticeComment(Long commentId,PrincipalDetails principalDetails){

        Comment comment = commentRepository.findNoticeCommentByWithParent(commentId)
                .orElseThrow(() -> new RestControllerException(ExceptionCode.COMMENT_NOT_FOUND));
        if(comment.getChildren().size() != 0) { // 자식이 있으면 상태만 변경
            comment.changeIsDeleted(true);
        } else { // 삭제 가능한 조상 댓글을 구해서 삭제
            commentRepository.delete(getDeletableAncestorComment(comment));
        }
    }

    @Transactional
    public void deleteImageComment(Long commentId,PrincipalDetails principalDetails) {
        Comment comment = commentRepository.findImageCommentByWithParent(commentId)
                .orElseThrow(() -> new RestControllerException(ExceptionCode.COMMENT_NOT_FOUND));
        if(comment.getChildren().size() != 0) { // 자식이 있으면 상태만 변경
            comment.changeIsDeleted(true);
        } else { // 삭제 가능한 조상 댓글을 구해서 삭제
            commentRepository.delete(getDeletableAncestorComment(comment));
        }
    }

    @Transactional
    public void deleteImageUrlComment(Long commentId,PrincipalDetails principalDetails) {
        Comment comment = commentRepository.findImageUrlCommentByWithParent(commentId)
                .orElseThrow(() -> new RestControllerException(ExceptionCode.COMMENT_NOT_FOUND));
        if(comment.getChildren().size() != 0) { // 자식이 있으면 상태만 변경
            comment.changeIsDeleted(true);
        } else { // 삭제 가능한 조상 댓글을 구해서 삭제
            commentRepository.delete(getDeletableAncestorComment(comment));
        }
    }

    @Transactional
    public void deleteAdConnectComment(Long commentId,PrincipalDetails principalDetails) {
        Comment comment = commentRepository.findAdConnectCommentByWithParent(commentId)
                .orElseThrow(() -> new RestControllerException(ExceptionCode.COMMENT_NOT_FOUND));
        if(comment.getChildren().size() != 0) { // 자식이 있으면 상태만 변경
            comment.changeIsDeleted(true);
        } else { // 삭제 가능한 조상 댓글을 구해서 삭제
            commentRepository.delete(getDeletableAncestorComment(comment));
        }
    }



    @Transactional
    public Comment getDeletableAncestorComment(Comment comment) {
        Comment parent = comment.getParent(); // 현재 댓글의 부모를 구함
        if (parent != null && parent.getChildren().size() == 1 && parent.getIsDeleted())
            // 부모가 있고, 부모의 자식이 1개(지금 삭제하는 댓글)이고, 부모의 삭제 상태가 TRUE인 댓글이라면 재귀
            return getDeletableAncestorComment(parent);
        return comment; // 삭제해야하는 댓글 반환
    }
}
