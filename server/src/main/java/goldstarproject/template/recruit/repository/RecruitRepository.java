package goldstarproject.template.recruit.repository;

import goldstarproject.template.recruit.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitRepository extends JpaRepository<Recruit, Long>,RecruitRepositoryCustom{
}
