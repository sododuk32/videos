package goldstarproject.template.notice.service.impl;

import goldstarproject.template.common.exception.ExceptionCode;
import goldstarproject.template.common.exception.RestControllerException;


import goldstarproject.template.community.board.dto.BoardsListDto;
import goldstarproject.template.community.board.entity.Board;
import goldstarproject.template.community.category.dto.CategoryRequestDto;
import goldstarproject.template.community.comment.repository.CommentRepository;
import goldstarproject.template.member.dto.MemberProfileDto;
import goldstarproject.template.member.entity.Member;
import goldstarproject.template.member.repository.MemberRepository;

import goldstarproject.template.member.service.MemberService;
import goldstarproject.template.notice.dto.NoticeListDto;
import goldstarproject.template.notice.dto.NoticeRequestDto;
import goldstarproject.template.notice.dto.NoticeResponseDto;
import goldstarproject.template.notice.entity.Notice;
import goldstarproject.template.notice.mapper.NoticeListResponseMapper;
import goldstarproject.template.notice.mapper.NoticeRequestMapper;
import goldstarproject.template.notice.mapper.NoticeResponseMapper;
import goldstarproject.template.notice.repository.NoticeRepository;
import goldstarproject.template.notice.service.NoticeService;
import goldstarproject.template.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final MemberService memberService;
    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final NoticeRequestMapper noticeRequestMapper;
    private final NoticeResponseMapper noticeResponseMapper;
    private final NoticeListResponseMapper noticeListResponseMapper;




    public NoticeResponseDto insertNotice(NoticeRequestDto noticeRequestDto, PrincipalDetails principalDetails) {
        Member member = memberService.detailMember(principalDetails.getMember().getId());
        Notice notice = noticeRequestMapper.toEntity(noticeRequestDto);
        notice.setWriter(member);
        notice.setIsTop(noticeRequestDto.getIsTop());
        notice.setCreatedAt(LocalDateTime.now());
        Notice savedNotice = noticeRepository.save(notice);
        return noticeResponseMapper.toDto(savedNotice);
    }


    @Transactional
    public NoticeResponseDto updateNotice(Long noticeId, NoticeRequestDto noticeRequestDto,PrincipalDetails principalDetails) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        Member member = memberService.detailMember(principalDetails.getMember().getId());
        noticeRequestMapper.toEntity(noticeRequestDto);
        Optional.ofNullable(noticeRequestDto.getTitle()).ifPresent(notice::setTitle);
        Optional.ofNullable(noticeRequestDto.getContent()).ifPresent(notice::setContent);
        notice.setUpdatedAt(LocalDateTime.now());
        notice.setWriter(member);
        notice.getViewCount();
        notice.setIsTop(noticeRequestDto.getIsTop());
        getTotalCommentCount(noticeId);
        Notice savedBoard = noticeRepository.save(notice);
        return noticeResponseMapper.toDto(savedBoard);
    }


    //단건의 게시글만 조회 (댓글 미포함)
    @Transactional
    public Notice detailNotice(Long id) {
        Notice notice = validateNotice(id);
        Long commentCount = getTotalCommentCount(notice.getId());
        getViewCount(notice.getId());
        notice.setCommentCount(commentCount.longValue());
        return notice;
    }


    //공지사항 중 isTop 이 true 인 게시물을 가장 먼저
    @Transactional(readOnly = true)
    public Page<NoticeListDto> findSortedNoticesWithPagination(int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        return noticeRepository.findSortedNoticesWithPagination(pageable);
    }



    //게시물 제목으로 검색
    public Page<NoticeListDto> searchNoticeList(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1,size,Sort.unsorted());

        Page<Notice> noticePage = noticeRepository.findByNoticeContaining(keyword,pageable);

        List<NoticeListDto> noticesList = noticePage.getContent().stream()
                .map(noticeListResponseMapper::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(noticesList, pageable,noticePage.getTotalElements());
    }


    //게시물 작성자로 검색
    public Page<NoticeListDto> searchNoticeWriter(String username,int page, int size) {
        Pageable pageable = PageRequest.of(page -1,size,Sort.unsorted());

        Page<Notice> noticePage = noticeRepository.findByNoticeWriter(username,pageable);

        List<NoticeListDto> noticesList = noticePage.getContent().stream()
                .map(noticeListResponseMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(noticesList,pageable,noticePage.getTotalElements());
    }


    @Transactional
    public void deleteNotice(Long noticeId,PrincipalDetails principalDetails) {
        Member member = memberService.detailMember(principalDetails.getMember().getId());
        Notice deleteNotice = noticeRepository.findById(noticeId).orElseThrow(
                () -> new RestControllerException(ExceptionCode.NOTICE_NOT_FOUND));
        noticeRepository.delete(deleteNotice);
    }


    @Transactional
    public void getViewCount(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        int totalViews = notice.getViewCount();
        notice.setViewCount(totalViews + 1);
        noticeRepository.save(notice);
    }



    @Transactional
    public Long getTotalCommentCount(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        long totalCommentCount = commentRepository.getTotalCommentCountByNoticeId(noticeId).intValue();
        notice.setCommentCount(totalCommentCount);
        noticeRepository.save(notice);
        return totalCommentCount;
    }


    public Notice validateNotice(Long id) {
        Optional<Notice> optionalNotice = noticeRepository.findById(id);
        Notice findNotice = optionalNotice.orElseThrow(()-> new RestControllerException(ExceptionCode.NOTICE_NOT_FOUND));
        return findNotice;
    }
}
