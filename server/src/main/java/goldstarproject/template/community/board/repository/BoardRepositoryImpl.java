package goldstarproject.template.community.board.repository;

import com.querydsl.core.QueryResults;

import com.querydsl.jpa.impl.JPAQueryFactory;

import goldstarproject.template.community.board.entity.Board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import static goldstarproject.template.community.board.entity.QBoard.board;
import static goldstarproject.template.community.category.entity.QCategory.category;



@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    //like count
    @Override
    public void updateBoardHeartCount(Board boardInstance, boolean b) {
        if (b) {
            queryFactory.update(board)
                    .set(board.likeCount, board.likeCount.add(1))
                    .where(board.eq(boardInstance))
                    .execute();
        } else {
            queryFactory.update(board)
                    .set(board.likeCount, board.likeCount.subtract(1))
                    .where(board.eq(boardInstance))
                    .execute();
        }
    }

    //카테고리별 게시물 가져오기
    @Override
    public Page<Board> findByCategoryName(String categoryName, Pageable pageable ) {
        QueryResults<Board> categoryResult = queryFactory.selectFrom(board)
                .innerJoin(board.category, category)
                .where(category.name.eq(categoryName))
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(categoryResult.getResults(),pageable, categoryResult.getTotal());
    }

    //게시물 제목으로 검색
    @Override
    public Page<Board> findByBoardContaining(String keyword, Pageable pageable) {
        QueryResults<Board> searchResults = queryFactory.selectFrom(board)
                .where(board.title.like("%" + keyword + "%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(searchResults.getResults(),pageable,searchResults.getTotal());
    }

    @Override
    public Page<Board> findByBoardWriter(String username, Pageable pageable) {
        QueryResults<Board> searchResults = queryFactory.selectFrom(board)
                .where(board.writer.username.like("%"+username + "%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(searchResults.getResults(),pageable, searchResults.getTotal());
    }
}
