package goldstarproject.template.storage.image_storage_02.repository;

import goldstarproject.template.storage.image_storage_02.entity.ImageUrlStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImageUrlStorageRepositoryCustom {
    void updateImagesHeartCount(ImageUrlStorage imageUrl, boolean b);

    Page<ImageUrlStorage> findByImagesContaining(String keyword, Pageable pageable);
    Page<ImageUrlStorage> findByImagesWriter(String username, Pageable pageable);



}
