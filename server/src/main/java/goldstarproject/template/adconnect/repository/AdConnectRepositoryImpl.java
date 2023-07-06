package goldstarproject.template.adconnect.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import goldstarproject.template.adconnect.entity.AdConnect;
import goldstarproject.template.community.board.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static goldstarproject.template.adconnect.entity.QAdConnect.adConnect;

@RequiredArgsConstructor
public class AdConnectRepositoryImpl implements AdConnectRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AdConnect> findByAdConnectContaining(String keyword, Pageable pageable) {
        QueryResults<AdConnect> searchResults = queryFactory.selectFrom(adConnect)
                .where(adConnect.title.like("%" + keyword + "%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(searchResults.getResults(),pageable,searchResults.getTotal());
    }

    @Override
    public Page<AdConnect> findByAdConnectWriter(String username, Pageable pageable) {
        QueryResults<AdConnect> searchResults = queryFactory.selectFrom(adConnect)
                .where(adConnect.writer.username.like("%"+ username + "%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(searchResults.getResults(),pageable, searchResults.getTotal());

    }
}
