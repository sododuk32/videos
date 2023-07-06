package goldstarproject.template.storage.image_storage_01.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import goldstarproject.template.storage.image_storage_01.entity.ImageStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static goldstarproject.template.storage.image_storage_01.entity.QImageStorage.imageStorage;

@RequiredArgsConstructor
public class ImageStorageRepositoryImpl implements ImageStorageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void updateImageHeartCount(ImageStorage image, boolean b) {
        if (b) {
            queryFactory.update(imageStorage)
                    .set(imageStorage.likeCount, imageStorage.likeCount.add(1))
                    .where(imageStorage.eq(image))
                    .execute();
        } else {
            queryFactory.update(imageStorage)
                    .set(imageStorage.likeCount, imageStorage.likeCount.subtract(1))
                    .where(imageStorage.eq(image))
                    .execute();
        }
    }

    @Override
    public Page<ImageStorage> findByImageContaining(String keyword, Pageable pageable) {
        QueryResults<ImageStorage> searchResults = queryFactory.selectFrom(imageStorage)
                .where(imageStorage.title.like("%" + keyword + "%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(searchResults.getResults(), pageable, searchResults.getTotal());
    }


    @Override
    public Page<ImageStorage> findByImageWriter(String username, Pageable pageable) {
        QueryResults<ImageStorage> searchResults = queryFactory.selectFrom(imageStorage)
                .where(imageStorage.writer.username.like("%" + username + "%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(searchResults.getResults(),pageable, searchResults.getTotal());
    }
}
