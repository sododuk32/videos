package goldstarproject.template.community.like.repository;

import goldstarproject.template.community.board.entity.Board;
import goldstarproject.template.community.like.entity.Heart;
import goldstarproject.template.question.entity.Question;
import goldstarproject.template.member.entity.Member;
import goldstarproject.template.notice.entity.Notice;
import goldstarproject.template.storage.image_storage_01.entity.ImageStorage;
import goldstarproject.template.storage.image_storage_02.entity.ImageUrlStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findByMemberAndBoard(Member member, Board board);  // 통합 자유게시판
    Optional<Heart> findByMemberAndNotice(Member member, Notice notice);  // 공지사항
    Optional<Heart> findByMemberAndQuestion(Member member, Question question);  // 질문 게시판
    Optional<Heart> findByMemberAndImage(Member member, ImageStorage image);  // 이미지 게시판 1
    Optional<Heart> findByMemberAndImages(Member member, ImageUrlStorage images);  // 이미지 게시판 2
}
