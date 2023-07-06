package goldstarproject.template.storage.image_storage_03.service.impl;

import goldstarproject.template.common.exception.ExceptionCode;
import goldstarproject.template.common.exception.RestControllerException;
import goldstarproject.template.community.comment.repository.CommentRepository;
import goldstarproject.template.member.entity.Member;
import goldstarproject.template.member.repository.MemberRepository;
import goldstarproject.template.member.service.MemberService;
import goldstarproject.template.security.auth.PrincipalDetails;
import goldstarproject.template.storage.image_storage_03.dto.LiveListDto;
import goldstarproject.template.storage.image_storage_03.dto.LiveRequestDto;
import goldstarproject.template.storage.image_storage_03.dto.LiveResponseDto;

import goldstarproject.template.storage.image_storage_03.entity.Live;
import goldstarproject.template.storage.image_storage_03.mapper.LiveListResponseMapper;
import goldstarproject.template.storage.image_storage_03.mapper.LiveRequestMapper;
import goldstarproject.template.storage.image_storage_03.mapper.LiveResponseMapper;
import goldstarproject.template.storage.image_storage_03.repository.LiveRepository;
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
public class LiveServiceImpl  {
    private final MemberService memberService;
    private final LiveRepository liveRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final LiveRequestMapper liveRequestMapper;
    private final LiveResponseMapper liveResponseMapper;
    private final LiveListResponseMapper liveListResponseMapper;


    public LiveResponseDto insertLive(LiveRequestDto liveRequestDto, PrincipalDetails principalDetails) {
        Member member = memberService.detailMember(principalDetails.getMember().getId());
        Live live = liveRequestMapper.toEntity(liveRequestDto);
        live.setWriter(member);
        live.setCreatedAt(LocalDateTime.now());
        Live savedlive = liveRepository.save(live);
        return liveResponseMapper.toDto(savedlive);
    }


    @Transactional
    public LiveResponseDto updateLive(Long liveId, LiveRequestDto liveRequestDto, PrincipalDetails principalDetails) {
        Live live = liveRepository.findById(liveId).orElseThrow(() -> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        Member member = memberService.detailMember(principalDetails.getMember().getId());
        liveRequestMapper.toEntity(liveRequestDto);
        Optional.ofNullable(liveRequestDto.getTitle()).ifPresent(live::setTitle);
        Optional.ofNullable(liveRequestDto.getContent()).ifPresent(live::setContent);
        Optional.ofNullable(liveRequestDto.getImageContent()).ifPresent(live::setImageContent);

        live.setUpdatedAt(LocalDateTime.now());
        live.setWriter(member);
        Live savedlive = liveRepository.save(live);
        return liveResponseMapper.toDto(savedlive);
    }


    //단건의 게시글만 조회 (댓글 미포함)
    @Transactional
    public Live detailLive(Long id) {
        Live live = validateLive(id);
        return live;
    }



    @Transactional(readOnly = true)
    public Page<LiveListDto> findAllLive(int page, int size) {
         Page<Live> livePage = liveRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
         return livePage.map(liveListResponseMapper::toDto);
    }


    @Transactional
    public void deleteLive(Long liveId,PrincipalDetails principalDetails) {
        Live deleteLive = liveRepository.findById(liveId).orElseThrow(
                () -> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        liveRepository.delete(deleteLive);
    }



    public Live validateLive(Long id) {
        Optional<Live> optionalLive = liveRepository.findById(id);
        Live findLive = optionalLive.orElseThrow(()-> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        return findLive;
    }
}
