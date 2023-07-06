package goldstarproject.template.community.like.entity;

import goldstarproject.template.common.BaseEntity;
import goldstarproject.template.community.board.entity.Board;
import goldstarproject.template.question.entity.Question;
import goldstarproject.template.member.entity.Member;
import goldstarproject.template.notice.entity.Notice;
import goldstarproject.template.storage.image_storage_01.entity.ImageStorage;
import goldstarproject.template.storage.image_storage_02.entity.ImageUrlStorage;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
@Getter
@Entity(name = "Hearts")
public class Heart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;  //통합 게시판

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice; //공지사항

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "image_id")
    private ImageStorage image;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "images_id")
    private ImageUrlStorage images;

}
