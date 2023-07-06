package goldstarproject.template.community.board.entity;

import goldstarproject.template.common.BaseEntity;
import goldstarproject.template.community.category.entity.Category;
import goldstarproject.template.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "boards")
public class Board extends BaseEntity {

    /**
     * ToDo
     * + [ Fix ] 게시물/작성자 검색 기능 (Like 조건을 통해 일부 해당하는 검색어는 전부 도출될 수 있어야함)
     * + [ Fix ] 총 댓글 갯수
     * + [ Fix ] 부모댓글 삭제 시 자식 댓글 삭제
     * + [ Fix ] 카테고리별게시물 목록 조회
     * + [ Fix ] 비밀글
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(name = "view_Count",nullable = false)
    @ColumnDefault("0")
    private Integer viewCount;
    @Column(name = "like_Count", nullable = false)
    @ColumnDefault("0")
    private Integer likeCount;
    @Column(name = "comment_Count", nullable = false)
    @ColumnDefault("0")
    private Long commentCount;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @Enumerated(EnumType.STRING)
    @Column(name = "board_type")
    private BoardType boardType;
}
