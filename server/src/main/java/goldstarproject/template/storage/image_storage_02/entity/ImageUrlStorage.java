package goldstarproject.template.storage.image_storage_02.entity;

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
@Entity(name = "images")
public class ImageUrlStorage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "images_id")
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column
    private String imageContent;
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
