package goldstarproject.template.adconnect.entity;

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
@Setter
@Getter
@Entity(name = "adConnects")
public class AdConnect extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adConnect_id")
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(name = "comment_Count", nullable = false)
    @ColumnDefault("0")
    private Long commentCount;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;
}
