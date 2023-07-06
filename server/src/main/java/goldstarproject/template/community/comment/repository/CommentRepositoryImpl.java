package goldstarproject.template.community.comment.repository;


import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import goldstarproject.template.community.comment.dto.CommentResponseDto;
import goldstarproject.template.community.comment.entity.Comment;
import goldstarproject.template.community.comment.entity.QComment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

import static goldstarproject.template.community.board.entity.QBoard.board;
import static goldstarproject.template.community.comment.entity.QComment.comment;
import static goldstarproject.template.notice.entity.QNotice.notice;
import static goldstarproject.template.question.entity.QQuestion.question;
import static goldstarproject.template.recruit.entity.QRecruit.recruit;


@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;



    //notice 댓글 + 대댓글 조회
    @Override
    public Page<CommentResponseDto> findByNoticeParentAndChild(Long id, Pageable pageable) {
        List<Comment> comments = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.notice.id.eq(id).or(comment.parent.id.eq(id)))
                .orderBy(comment.parent.id.desc().nullsFirst(), comment.createdAt.desc())
                .fetch();

        // 전체 댓글 개수
        int totalComments = comments.size();

        // 페이징 처리
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int startIndex = pageNumber * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalComments);

        // 유효한 범위 내에 있는지 확인
        if (startIndex > endIndex || startIndex > totalComments) {
            // 유효한 범위 내에 댓글이 없음
            return new PageImpl<>(Collections.emptyList(), pageable, totalComments);
        }

        List<Comment> paginatedComments = comments.subList(startIndex, endIndex);

        // Comment 객체를 CommentResponseDto 객체로 변환
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        Map<Long, CommentResponseDto> commentDtoHashMap = new HashMap<>();

        for (Comment c : paginatedComments) {
            CommentResponseDto commentResponseDto = CommentResponseDto.convertCommentToDto(c);
            commentDtoHashMap.put(commentResponseDto.getId(), commentResponseDto);

            Comment parent = c.getParent();
            if (parent != null) {
                CommentResponseDto parentDto = commentDtoHashMap.get(parent.getId());
                if (parentDto != null) {
                    parentDto.getChildren().add(commentResponseDto);
                }
            } else {
                commentResponseDtoList.add(commentResponseDto);
            }
        }

        return new PageImpl<>(commentResponseDtoList, pageable, totalComments);
    }




    //recruit 댓글 + 대댓글 조회
    @Override
    public Page<CommentResponseDto> findByRecruitParentAndChild(Long id, Pageable pageable) {
        List<Comment> comments = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.recruit.id.eq(id).or(comment.parent.id.eq(id)))
                .orderBy(comment.parent.id.desc().nullsFirst(), comment.createdAt.desc())
                .fetch();

        // 전체 댓글 개수
        int totalComments = comments.size();

        // 페이징 처리
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int startIndex = pageNumber * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalComments);

        // 유효한 범위 내에 있는지 확인
        if (startIndex > endIndex || startIndex > totalComments) {
            // 유효한 범위 내에 댓글이 없음
            return new PageImpl<>(Collections.emptyList(), pageable, totalComments);
        }

        List<Comment> paginatedComments = comments.subList(startIndex, endIndex);

        // Comment 객체를 CommentResponseDto 객체로 변환
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        Map<Long, CommentResponseDto> commentDtoHashMap = new HashMap<>();

        for (Comment c : paginatedComments) {
            CommentResponseDto commentResponseDto = CommentResponseDto.convertCommentToDto(c);
            commentDtoHashMap.put(commentResponseDto.getId(), commentResponseDto);

            Comment parent = c.getParent();
            if (parent != null) {
                CommentResponseDto parentDto = commentDtoHashMap.get(parent.getId());
                if (parentDto != null) {
                    parentDto.getChildren().add(commentResponseDto);
                }
            } else {
                commentResponseDtoList.add(commentResponseDto);
            }
        }

        return new PageImpl<>(commentResponseDtoList, pageable, totalComments);
    }




    //question 댓글 + 대댓글 조회
    @Override
    public Page<CommentResponseDto> findByQuestionParentAndChild(Long id, Pageable pageable) {
        List<Comment> comments = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.question.id.eq(id).or(comment.parent.id.eq(id)))
                .orderBy(comment.parent.id.desc().nullsFirst(), comment.createdAt.desc())
                .fetch();

        // 전체 댓글 개수
        int totalComments = comments.size();

        // 페이징 처리
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int startIndex = pageNumber * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalComments);

        // 유효한 범위 내에 있는지 확인
        if (startIndex > endIndex || startIndex > totalComments) {
            // 유효한 범위 내에 댓글이 없음
            return new PageImpl<>(Collections.emptyList(), pageable, totalComments);
        }

        List<Comment> paginatedComments = comments.subList(startIndex, endIndex);

        // Comment 객체를 CommentResponseDto 객체로 변환
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        Map<Long, CommentResponseDto> commentDtoHashMap = new HashMap<>();

        for (Comment c : paginatedComments) {
            CommentResponseDto commentResponseDto = CommentResponseDto.convertCommentToDto(c);
            commentDtoHashMap.put(commentResponseDto.getId(), commentResponseDto);

            Comment parent = c.getParent();
            if (parent != null) {
                CommentResponseDto parentDto = commentDtoHashMap.get(parent.getId());
                if (parentDto != null) {
                    parentDto.getChildren().add(commentResponseDto);
                }
            } else {
                commentResponseDtoList.add(commentResponseDto);
            }
        }

        return new PageImpl<>(commentResponseDtoList, pageable, totalComments);
    }



    @Override
    public Page<CommentResponseDto> findByImageParentAndChild(Long id, Pageable pageable) {
        List<Comment> comments = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.image.id.eq(id).or(comment.parent.id.eq(id)))
                .orderBy(comment.parent.id.desc().nullsFirst(), comment.createdAt.desc())
                .fetch();

        // 전체 댓글 개수
        int totalComments = comments.size();

        // 페이징 처리
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int startIndex = pageNumber * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalComments);

        // 유효한 범위 내에 있는지 확인
        if (startIndex > endIndex || startIndex > totalComments) {
            // 유효한 범위 내에 댓글이 없음
            return new PageImpl<>(Collections.emptyList(), pageable, totalComments);
        }

        List<Comment> paginatedComments = comments.subList(startIndex, endIndex);

        // Comment 객체를 CommentResponseDto 객체로 변환
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        Map<Long, CommentResponseDto> commentDtoHashMap = new HashMap<>();

        for (Comment c : paginatedComments) {
            CommentResponseDto commentResponseDto = CommentResponseDto.convertCommentToDto(c);
            commentDtoHashMap.put(commentResponseDto.getId(), commentResponseDto);

            Comment parent = c.getParent();
            if (parent != null) {
                CommentResponseDto parentDto = commentDtoHashMap.get(parent.getId());
                if (parentDto != null) {
                    parentDto.getChildren().add(commentResponseDto);
                }
            } else {
                commentResponseDtoList.add(commentResponseDto);
            }
        }

        return new PageImpl<>(commentResponseDtoList, pageable, totalComments);
    }

    @Override
    public Page<CommentResponseDto> findByImagesUrlParentAndChild(Long id, Pageable pageable) {
        List<Comment> comments = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.images.id.eq(id).or(comment.parent.id.eq(id)))
                .orderBy(comment.parent.id.desc().nullsFirst(), comment.createdAt.desc())
                .fetch();

        // 전체 댓글 개수
        int totalComments = comments.size();

        // 페이징 처리
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int startIndex = pageNumber * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalComments);

        // 유효한 범위 내에 있는지 확인
        if (startIndex > endIndex || startIndex > totalComments) {
            // 유효한 범위 내에 댓글이 없음
            return new PageImpl<>(Collections.emptyList(), pageable, totalComments);
        }

        List<Comment> paginatedComments = comments.subList(startIndex, endIndex);

        // Comment 객체를 CommentResponseDto 객체로 변환
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        Map<Long, CommentResponseDto> commentDtoHashMap = new HashMap<>();

        for (Comment c : paginatedComments) {
            CommentResponseDto commentResponseDto = CommentResponseDto.convertCommentToDto(c);
            commentDtoHashMap.put(commentResponseDto.getId(), commentResponseDto);

            Comment parent = c.getParent();
            if (parent != null) {
                CommentResponseDto parentDto = commentDtoHashMap.get(parent.getId());
                if (parentDto != null) {
                    parentDto.getChildren().add(commentResponseDto);
                }
            } else {
                commentResponseDtoList.add(commentResponseDto);
            }
        }

        return new PageImpl<>(commentResponseDtoList, pageable, totalComments);
    }


    @Override
    public Page<CommentResponseDto> findByBoardParentAndChild(Long id, Pageable pageable) {
        List<Comment> comments = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.board.id.eq(id).or(comment.parent.id.eq(id)))
                .orderBy(comment.parent.id.desc().nullsFirst(), comment.createdAt.desc())
                .fetch();

        // 전체 댓글 개수
        int totalComments = comments.size();

        // 페이징 처리
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int startIndex = pageNumber * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalComments);

        // 유효한 범위 내에 있는지 확인
        if (startIndex > endIndex || startIndex > totalComments) {
            // 유효한 범위 내에 댓글이 없음
            return new PageImpl<>(Collections.emptyList(), pageable, totalComments);
        }

        List<Comment> paginatedComments = comments.subList(startIndex, endIndex);

        // Comment 객체를 CommentResponseDto 객체로 변환
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        Map<Long, CommentResponseDto> commentDtoHashMap = new HashMap<>();

        for (Comment c : paginatedComments) {
            CommentResponseDto commentResponseDto = CommentResponseDto.convertCommentToDto(c);
            commentDtoHashMap.put(commentResponseDto.getId(), commentResponseDto);

            Comment parent = c.getParent();
            if (parent != null) {
                CommentResponseDto parentDto = commentDtoHashMap.get(parent.getId());
                if (parentDto != null) {
                    parentDto.getChildren().add(commentResponseDto);
                }
            } else {
                commentResponseDtoList.add(commentResponseDto);
            }
        }

        return new PageImpl<>(commentResponseDtoList, pageable, totalComments);
    }

    @Override
    public Page<CommentResponseDto> findByAdConnectsParentAndChild(Long id, Pageable pageable) {
        List<Comment> comments = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.adConnect.id.eq(id).or(comment.parent.id.eq(id)))
                .orderBy(comment.parent.id.desc().nullsFirst(), comment.createdAt.desc())
                .fetch();

        // 전체 댓글 개수
        int totalComments = comments.size();

        // 페이징 처리
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int startIndex = pageNumber * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalComments);

        // 유효한 범위 내에 있는지 확인
        if (startIndex > endIndex || startIndex > totalComments) {
            // 유효한 범위 내에 댓글이 없음
            return new PageImpl<>(Collections.emptyList(), pageable, totalComments);
        }

        List<Comment> paginatedComments = comments.subList(startIndex, endIndex);

        // Comment 객체를 CommentResponseDto 객체로 변환
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        Map<Long, CommentResponseDto> commentDtoHashMap = new HashMap<>();

        for (Comment c : paginatedComments) {
            CommentResponseDto commentResponseDto = CommentResponseDto.convertCommentToDto(c);
            commentDtoHashMap.put(commentResponseDto.getId(), commentResponseDto);

            Comment parent = c.getParent();
            if (parent != null) {
                CommentResponseDto parentDto = commentDtoHashMap.get(parent.getId());
                if (parentDto != null) {
                    parentDto.getChildren().add(commentResponseDto);
                }
            } else {
                commentResponseDtoList.add(commentResponseDto);
            }
        }
        return new PageImpl<>(commentResponseDtoList, pageable, totalComments);
    }




    //코멘트 삭제 시 조회 메서드
    @Override
    public Optional<Comment> findBoardCommentByIdWithParent(Long id) {
        Comment selectedComment = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .join(comment.board, board).fetchJoin()
                .where(comment.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(selectedComment);
    }

    @Override
    public Optional<Comment> findNoticeCommentByWithParent(Long id) {
        Comment selectedComment = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .join(comment.notice, notice).fetchJoin()
                .where(comment.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(selectedComment);
    }

    @Override
    public Optional<Comment> findQuestionCommentByWithParent(Long id) {
        Comment selectedComment = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .join(comment.question, question).fetchJoin()
                .where(comment.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(selectedComment);
    }

    @Override
    public Optional<Comment> findRecruitCommentByWithParent(Long id) {
        Comment selectedComment = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .join(comment.recruit, recruit).fetchJoin()
                .where(comment.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(selectedComment);
    }

    @Override
    public Optional<Comment> findImageCommentByWithParent(Long id) {
        Comment selectedComment = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .join(comment.image, comment.image).fetchJoin()
                .where(comment.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(selectedComment);
    }

    @Override
    public Optional<Comment> findImageUrlCommentByWithParent(Long id) {
        Comment selectedComment = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .join(comment.images, comment.images).fetchJoin()
                .where(comment.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(selectedComment);
    }

    @Override
    public Optional<Comment> findAdConnectCommentByWithParent(Long id) {
        Comment selectedComment = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .join(comment.adConnect, comment.adConnect).fetchJoin()
                .where(comment.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(selectedComment);
    }



    //게시판 댓긁 + 대댓글 총 합계
    @Override
    public Long getTotalCommentCountByBoardId(Long boardId) {
        return queryFactory.select(comment.id.count())
                .from(comment)
                .where(comment.board.id.eq(boardId))
                .fetchOne();
    }

    //공지 댓글 + 대댓글 총 합계
    @Override
    public Long getTotalCommentCountByNoticeId(Long noticeId) {
        return queryFactory.select(comment.id.count())
                .from(comment)
                .where(comment.notice.id.eq(noticeId))
                .fetchOne();
    }

    //구인구직 게시판 댓글 + 대댓글 총 합계
    @Override
    public Long getTotalCommentCountByRecruitId(Long recruitId) {
        return queryFactory.select(comment.id.count())
                .from(comment)
                .where(comment.recruit.id.eq(recruitId))
                .fetchOne();
    }

    //질문게시판 댓글 + 대댓글 총 합계
    @Override
    public Long getTotalCommentCountByQuestionId(Long questionId) {
        return queryFactory.select(comment.id.count())
                .from(comment)
                .where(comment.question.id.eq(questionId))
                .fetchOne();
    }

    @Override
    public Long getTotalCommentCountByImageId(Long imageId) {
        return queryFactory.select(comment.id.count())
                .from(comment)
                .where(comment.image.id.eq(imageId))
                .fetchOne();
    }

    @Override
    public Long getTotalCommentCountByImagesId(Long imagesId) {
        return queryFactory.select(comment.id.count())
                .from(comment)
                .where(comment.images.id.eq(imagesId))
                .fetchOne();
    }

    @Override
    public Long getTotalCommentCountByAdConnectId(Long adConnectId) {
        return queryFactory.select(comment.id.count())
                .from(comment)
                .where(comment.adConnect.id.eq(adConnectId))
                .fetchOne();
    }
}
