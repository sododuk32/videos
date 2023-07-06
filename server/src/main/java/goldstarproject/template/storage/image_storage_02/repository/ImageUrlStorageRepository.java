package goldstarproject.template.storage.image_storage_02.repository;

import goldstarproject.template.storage.image_storage_02.entity.ImageUrlStorage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageUrlStorageRepository extends JpaRepository<ImageUrlStorage, Long>, ImageUrlStorageRepositoryCustom {
}
