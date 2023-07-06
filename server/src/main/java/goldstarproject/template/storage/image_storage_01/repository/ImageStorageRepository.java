package goldstarproject.template.storage.image_storage_01.repository;

import goldstarproject.template.storage.image_storage_01.entity.ImageStorage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageStorageRepository extends JpaRepository<ImageStorage, Long>, ImageStorageRepositoryCustom {
}
