package goldstarproject.template.storage.image_storage_01.repository;

import goldstarproject.template.storage.image_storage_01.entity.ImageStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImageStorageRepositoryCustom {
    void updateImageHeartCount(ImageStorage image, boolean b);
    Page<ImageStorage> findByImageContaining(String keyword, Pageable pageable);
    Page<ImageStorage> findByImageWriter(String username, Pageable pageable);

}
