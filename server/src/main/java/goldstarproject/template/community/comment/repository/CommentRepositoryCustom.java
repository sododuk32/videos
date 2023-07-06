package goldstarproject.template.community.comment.repository;

import goldstarproject.template.community.comment.dto.CommentResponseDto;
import goldstarproject.template.community.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface CommentRepositoryCustom {



    Page<CommentResponseDto> findByNoticeParentAndChild(Long id, Pageable pageable);
    Page<CommentResponseDto> findByRecruitParentAndChild(Long id, Pageable pageable);
    Page<CommentResponseDto> findByQuestionParentAndChild(Long id, Pageable pageable);
    Page<CommentResponseDto> findByImageParentAndChild(Long id, Pageable pageable);
    Page<CommentResponseDto> findByImagesUrlParentAndChild(Long id, Pageable pageable);
    Page<CommentResponseDto> findByBoardParentAndChild(Long id, Pageable pageable);
    Page<CommentResponseDto> findByAdConnectsParentAndChild(Long id,Pageable pageable);




    //삭제 시 사용 쿼리
    Optional<Comment> findBoardCommentByIdWithParent(Long id);
    Optional<Comment> findNoticeCommentByWithParent(Long id);
    Optional<Comment> findQuestionCommentByWithParent(Long id);
    Optional<Comment> findRecruitCommentByWithParent(Long id);
    Optional<Comment> findImageCommentByWithParent(Long id);
    Optional<Comment> findImageUrlCommentByWithParent(Long id);
    Optional<Comment> findAdConnectCommentByWithParent(Long id);




    Long getTotalCommentCountByBoardId(Long id);
    Long getTotalCommentCountByNoticeId(Long noticeId);
    Long getTotalCommentCountByRecruitId(Long recruitId);
    Long getTotalCommentCountByQuestionId(Long questionId);
    Long getTotalCommentCountByImageId(Long imageId);
    Long getTotalCommentCountByImagesId(Long imagesId);
    Long getTotalCommentCountByAdConnectId(Long adConnectId);
}
