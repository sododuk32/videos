package goldstarproject.template.recruit.repository;

import goldstarproject.template.recruit.entity.Recruit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecruitRepositoryCustom {

    Page<Recruit> findByRecruitContaining(String keyword, Pageable pageable);
    Page<Recruit> findByRecruitWriter(String keyword, Pageable pageable);

}
