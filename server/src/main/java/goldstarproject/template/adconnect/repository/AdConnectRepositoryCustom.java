package goldstarproject.template.adconnect.repository;

import goldstarproject.template.adconnect.entity.AdConnect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdConnectRepositoryCustom {

    Page<AdConnect> findByAdConnectContaining(String keyword, Pageable pageable);
    Page<AdConnect> findByAdConnectWriter(String username,Pageable pageable);

}
