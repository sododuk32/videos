package goldstarproject.template.notice.entity;

import goldstarproject.template.common.BaseEntity;
import goldstarproject.template.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter  //추후 매퍼를 인스턴스 메서드 + 제네릭매퍼로 처리해보자
@Entity(name = "notices")
public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(nullable = false,columnDefinition = "TINYINT(1) DEFAULT 0") //삭제상태 (1) 정상상태 (0)
    private Boolean isTop;
    @Column(name = "view_Count", nullable = false)
    @ColumnDefault("0")
    private Integer viewCount;
    @Column(name = "like_Count", nullable = false)
    @ColumnDefault("0")
    private Integer likeCount;
    @Column(name = "comment_Count", nullable = false)
    @ColumnDefault("0")
    private Long commentCount;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "admin_id")
    private Member writer;
}
