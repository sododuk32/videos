package goldstarproject.template.community.comment.repository;

import goldstarproject.template.adconnect.entity.AdConnect;
import goldstarproject.template.community.board.entity.Board;
import goldstarproject.template.community.comment.entity.Comment;
import goldstarproject.template.notice.entity.Notice;
import goldstarproject.template.question.entity.Question;
import goldstarproject.template.recruit.entity.Recruit;
import goldstarproject.template.storage.image_storage_01.entity.ImageStorage;
import goldstarproject.template.storage.image_storage_02.entity.ImageUrlStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>,CommentRepositoryCustom {

    List<Comment> findByBoardId(Long boardId);



    Optional<Comment> findByIdAndQuestion(Long commentId, Question question);
    Optional<Comment> findByIdAndNotice(Long commentId, Notice notice);
    Optional<Comment> findByIdAndRecruit(Long commentId, Recruit recruit);
    Optional<Comment> findByIdAndImage(Long commentId, ImageStorage image);
    Optional<Comment> findByIdAndImages(Long commentId, ImageUrlStorage images);
    Optional<Comment> findByIdAndAdConnect(Long commentId, AdConnect adConnect);
    Optional<Comment> findByIdAndBoard(Long commentId, Board board);


}
