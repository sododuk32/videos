package goldstarproject.template.recruit.service.impl;

import goldstarproject.template.common.exception.ExceptionCode;
import goldstarproject.template.common.exception.RestControllerException;

import goldstarproject.template.community.comment.repository.CommentRepository;
import goldstarproject.template.member.dto.MemberProfileDto;
import goldstarproject.template.member.entity.Member;
import goldstarproject.template.member.repository.MemberRepository;
import goldstarproject.template.member.service.MemberService;
import goldstarproject.template.notice.dto.NoticeListDto;
import goldstarproject.template.notice.entity.Notice;
import goldstarproject.template.recruit.dto.RecruitListDto;
import goldstarproject.template.recruit.dto.RecruitRequestDto;
import goldstarproject.template.recruit.dto.RecruitResponseDto;
import goldstarproject.template.recruit.entity.Recruit;
import goldstarproject.template.recruit.mapper.RecruitListResponseMapper;
import goldstarproject.template.recruit.mapper.RecruitRequestMapper;
import goldstarproject.template.recruit.mapper.RecruitResponseMapper;
import goldstarproject.template.recruit.repository.RecruitRepository;
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
@Transactional
@RequiredArgsConstructor
public class RecruitServiceImpl {
    private final MemberService memberService;
    private final RecruitRepository recruitRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final RecruitRequestMapper recruitRequestMapper;
    private final RecruitResponseMapper recruitResponseMapper;
    private final RecruitListResponseMapper recruitListResponseMapper;


    public RecruitResponseDto insertRecruit(RecruitRequestDto recruitRequestDto, PrincipalDetails principalDetails) {
        Member member = memberService.detailMember(principalDetails.getMember().getId());
        Recruit recruit = recruitRequestMapper.toEntity(recruitRequestDto);
        recruit.setWriter(member);
        recruit.setCreatedAt(LocalDateTime.now());
        Recruit savedRecruit = recruitRepository.save(recruit);
        return recruitResponseMapper.toDto(savedRecruit);
    }


    @Transactional
    public RecruitResponseDto updateRecruit(Long recruitId, RecruitRequestDto recruitRequestDto,PrincipalDetails principalDetails) {
        Recruit recruit = recruitRepository.findById(recruitId).orElseThrow(() -> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        Member member = memberService.detailMember(principalDetails.getMember().getId());
        recruitRequestMapper.toEntity(recruitRequestDto);
        Optional.ofNullable(recruitRequestDto.getTitle()).ifPresent(recruit::setTitle);
        Optional.ofNullable(recruitRequestDto.getContent()).ifPresent(recruit::setContent);
        recruit.setUpdatedAt(LocalDateTime.now());
        recruit.setWriter(member);
        recruit.getViewCount();
        getTotalCommentCount(recruitId);
        Recruit savedRecruit = recruitRepository.save(recruit);
        return recruitResponseMapper.toDto(savedRecruit);
    }


    //단건의 게시글만 조회 (댓글 미포함)
    @Transactional
    public Recruit detailRecruit(Long id) {
        Recruit recruit = validateRecruit(id);
        Long commentCount = getTotalCommentCount(recruit.getId());
        recruit.setCommentCount(commentCount.longValue());
        getViewCount(recruit.getId());
        return recruit;
    }



    @Transactional(readOnly = true)
    public Page<RecruitListDto> findAllRecruit(int page, int size) {
        Page<Recruit> recruitPage = recruitRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
         return recruitPage.map(recruitListResponseMapper::toDto);
    }


    //게시물 제목으로 검색
    public Page<RecruitListDto> searchRecruitList(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1,size,Sort.unsorted());

        Page<Recruit> recruitsPage = recruitRepository.findByRecruitContaining(keyword,pageable);

        List<RecruitListDto> recruitList = recruitsPage.getContent().stream()
                .map(recruitListResponseMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(recruitList, pageable,recruitsPage.getTotalElements());
    }


    //게시물 작성자로 검색
    public Page<RecruitListDto> searchRecruitWriter(String username,int page, int size) {
        Pageable pageable = PageRequest.of(page -1,size,Sort.unsorted());

        Page<Recruit> recruitsPage = recruitRepository.findByRecruitWriter(username,pageable);

        List<RecruitListDto> noticesList = recruitsPage.getContent().stream()
                .map(recruitListResponseMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(noticesList,pageable,recruitsPage.getTotalElements());
    }





    @Transactional
    public void deleteRecruit(Long recruitId,PrincipalDetails principalDetails) {
        Member member = memberService.detailMember(principalDetails.getMember().getId());
        Recruit deleteRecruit = recruitRepository.findById(recruitId).orElseThrow(
                () -> new RestControllerException(ExceptionCode.NOTICE_NOT_FOUND));
        recruitRepository.delete(deleteRecruit);
    }



    @Transactional
    public void getViewCount(Long recruitId) {
        Recruit recruit = recruitRepository.findById(recruitId).orElseThrow(() -> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        int totalViews = recruit.getViewCount();
        recruit.setViewCount(totalViews + 1);
        recruitRepository.save(recruit);
    }


    @Transactional
    public Long getTotalCommentCount(Long recruitId) {
        Recruit recruit = recruitRepository.findById(recruitId).orElseThrow(() -> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        Long totalCommentCount = commentRepository.getTotalCommentCountByRecruitId(recruitId);
        recruit.setCommentCount(totalCommentCount);
        recruitRepository.save(recruit);
        return totalCommentCount;
    }



    public Recruit validateRecruit(Long id) {
        Optional<Recruit> optionalRecruit = recruitRepository.findById(id);
        Recruit findRecruit = optionalRecruit.orElseThrow(()-> new RestControllerException(ExceptionCode.NOTICE_NOT_FOUND));
        return findRecruit;
    }
}
