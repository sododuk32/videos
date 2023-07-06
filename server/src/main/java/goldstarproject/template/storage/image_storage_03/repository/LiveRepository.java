package goldstarproject.template.storage.image_storage_03.repository;


import goldstarproject.template.storage.image_storage_03.entity.Live;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveRepository extends JpaRepository<Live, Long> {
}
