package goldstarproject.template.storage.image_storage_02.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import goldstarproject.template.storage.image_storage_02.entity.ImageUrlStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.util.List;

import static goldstarproject.template.storage.image_storage_01.entity.QImageStorage.imageStorage;
import static goldstarproject.template.storage.image_storage_02.entity.QImageUrlStorage.imageUrlStorage;

@RequiredArgsConstructor
public class ImageUrlStorageRepositoryImpl implements ImageUrlStorageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void updateImagesHeartCount(ImageUrlStorage imageUrl, boolean b) {
        if (b) {
            queryFactory.update(imageUrlStorage)
                    .set(imageUrlStorage.likeCount, imageUrlStorage.likeCount.add(1))
                    .where(imageUrlStorage.eq(imageUrl))
                    .execute();
        } else {
            queryFactory.update(imageUrlStorage)
                    .set(imageUrlStorage.likeCount, imageUrlStorage.likeCount.subtract(1))
                    .where(imageUrlStorage.eq(imageUrl))
                    .execute();
        }
    }

    @Override
    public Page<ImageUrlStorage> findByImagesContaining(String keyword, Pageable pageable) {
        QueryResults<ImageUrlStorage> searchResults = queryFactory.selectFrom(imageUrlStorage)
                .where(imageUrlStorage.title.like("%" + keyword + "%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(searchResults.getResults(), pageable, searchResults.getTotal());
    }


    /**
     * fetchResult Deprecated issue
     */




    @Override
    public Page<ImageUrlStorage> findByImagesWriter(String username, Pageable pageable) {
        QueryResults<ImageUrlStorage> searchResults = queryFactory.selectFrom(imageUrlStorage)
                .where(imageUrlStorage.writer.username.like("%" + username + "%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(searchResults.getResults(),pageable, searchResults.getTotal());
    }
}
