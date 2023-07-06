package goldstarproject.template.community.comment.entity;

import goldstarproject.template.adconnect.entity.AdConnect;
import goldstarproject.template.common.BaseEntity;
import goldstarproject.template.community.board.entity.Board;
import goldstarproject.template.member.entity.Member;
import goldstarproject.template.notice.entity.Notice;
import goldstarproject.template.question.entity.Question;
import goldstarproject.template.recruit.entity.Recruit;
import goldstarproject.template.storage.image_storage_01.entity.ImageStorage;
import goldstarproject.template.storage.image_storage_02.entity.ImageUrlStorage;
import goldstarproject.template.storage.image_storage_03.entity.Live;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DynamicInsert
@Entity(name = "comments")
public class Comment extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id",unique = true)
    private Long id;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0") //삭제상태 (1) 정상상태 (0)
    private Boolean isDeleted;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    //기존 댓글에 대댓글을 달 경우 부모 댓글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;


    @OneToMany(mappedBy = "parent",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Comment> children = new ArrayList<>();

    @Column(name = "children_Count",nullable = false)
    @ColumnDefault("0")
    private Integer childrenCount;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "image_id")
    private ImageStorage image;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "images_id")
    private ImageUrlStorage images;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "adConnect_id")
    private AdConnect adConnect;




    public void updateParent(Comment comment) {
        this.parent = comment;
    }

    public void updateBoard(Board board) {
        this.board = board;
    }

    public void updateWriter(Member member) {
        this.writer = member;
    }

    public void changeIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
