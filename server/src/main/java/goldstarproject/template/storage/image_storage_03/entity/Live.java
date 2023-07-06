package goldstarproject.template.storage.image_storage_03.entity;

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
@Entity(name = "lives")
public class Live extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "live_id")
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column
    private String imageContent;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "admin_id")
    private Member writer;
}
