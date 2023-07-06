package goldstarproject.template.adconnect.repository;

import goldstarproject.template.adconnect.entity.AdConnect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdConnectRepository extends JpaRepository<AdConnect, Long>, AdConnectRepositoryCustom {
}
