package goldstarproject.template.storage.image_storage_01.service;

public interface ImageStorageService {
    void getViewCount(Long imageId);
    Long getTotalCommentCount(Long imageId);

}
