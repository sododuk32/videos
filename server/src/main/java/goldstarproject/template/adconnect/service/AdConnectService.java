package goldstarproject.template.adconnect.service;

import goldstarproject.template.adconnect.dto.AdConnectListDto;
import goldstarproject.template.adconnect.dto.AdConnectRequestDto;
import goldstarproject.template.adconnect.dto.AdConnectResponseDto;
import goldstarproject.template.adconnect.entity.AdConnect;
import goldstarproject.template.adconnect.mapper.AdConnectListResponseMapper;
import goldstarproject.template.adconnect.mapper.AdConnectRequestMapper;
import goldstarproject.template.adconnect.mapper.AdConnectResponseMapper;
import goldstarproject.template.adconnect.repository.AdConnectRepository;
import goldstarproject.template.common.exception.ExceptionCode;
import goldstarproject.template.common.exception.RestControllerException;
import goldstarproject.template.community.comment.repository.CommentRepository;
import goldstarproject.template.member.dto.MemberProfileDto;
import goldstarproject.template.member.entity.Member;
import goldstarproject.template.member.repository.MemberRepository;
import goldstarproject.template.member.service.MemberService;
import goldstarproject.template.notice.dto.NoticeListDto;
import goldstarproject.template.notice.dto.NoticeRequestDto;
import goldstarproject.template.notice.dto.NoticeResponseDto;
import goldstarproject.template.notice.entity.Notice;
import goldstarproject.template.recruit.entity.Recruit;
import goldstarproject.template.security.auth.PrincipalDetails;
import goldstarproject.template.storage.image_storage_02.entity.ImageUrlStorage;
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
public class AdConnectService {

    private final AdConnectRequestMapper adConnectRequestMapper;
    private final AdConnectResponseMapper adConnectResponseMapper;
    private final AdConnectRepository adConnectRepository;
    private final MemberService memberService;
    private final AdConnectListResponseMapper adConnectListResponseMapper;
    private final CommentRepository commentRepository;


    @Transactional
    public AdConnectResponseDto insertAdConnect(AdConnectRequestDto adConnectRequestDto, PrincipalDetails principalDetails) {
        Member member = memberService.validateMember(principalDetails.getMember().getId());
        AdConnect adConnect = adConnectRequestMapper.toEntity(adConnectRequestDto);
        adConnect.setWriter(member);
        adConnect.setCreatedAt(LocalDateTime.now());
        AdConnect savedAdConnect = adConnectRepository.save(adConnect);
        return adConnectResponseMapper.toDto(savedAdConnect);
    }


    @Transactional
    public AdConnectResponseDto updateAdConnect(Long adConnectId, AdConnectRequestDto adConnectRequestDto,PrincipalDetails principalDetails) {
        AdConnect adConnect = adConnectRepository.findById(adConnectId).orElseThrow(() -> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        Member member = memberService.validateMember(principalDetails.getMember().getId());
        adConnectRequestMapper.toEntity(adConnectRequestDto);
        Optional.ofNullable(adConnectRequestDto.getTitle()).ifPresent(adConnect::setTitle);
        Optional.ofNullable(adConnectRequestDto.getContent()).ifPresent(adConnect::setContent);
        adConnect.setUpdatedAt(LocalDateTime.now());
        adConnect.setWriter(member);
        AdConnect savedAdConnect = adConnectRepository.save(adConnect);
        return adConnectResponseMapper.toDto(savedAdConnect);
    }


    //단건의 게시글만 조회 (댓글 미포함)
    @Transactional(readOnly = true)
    public AdConnect detailAdConnect(Long adConnectsId) {
        AdConnect adConnect = validateAdConnect(adConnectsId);
        Long commentCount = getTotalCommentCount(adConnect.getId());
        adConnect.setCommentCount(commentCount.longValue());
        return adConnect;
    }



    @Transactional(readOnly = true)
    public Page<AdConnectListDto> findAllAdConnect(int page, int size) {
        Page<AdConnect> adConnectPage = adConnectRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
        return adConnectPage.map(adConnectListResponseMapper::toDto);
    }


    //게시물 제목으로 검색
    public Page<AdConnectListDto> searchAdConnectList(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1,size,Sort.unsorted());

        Page<AdConnect> adConnectPage = adConnectRepository.findByAdConnectContaining(keyword,pageable);

        List<AdConnectListDto> adConnectList = adConnectPage.getContent().stream()
                .map(adConnectListResponseMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(adConnectList, pageable,adConnectPage.getTotalElements());
    }


    //게시물 작성자로 검색
    public Page<AdConnectListDto> searchAdConnectWriter(String username,int page, int size) {
        Pageable pageable = PageRequest.of(page -1,size,Sort.unsorted());

        Page<AdConnect> adConnectPage = adConnectRepository.findByAdConnectWriter(username,pageable);

        List<AdConnectListDto> adConnectList = adConnectPage.getContent().stream()
                .map(adConnectListResponseMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(adConnectList,pageable,adConnectPage.getTotalElements());
    }


    @Transactional
    public void deleteAdConnect(Long adConnectId,PrincipalDetails principalDetails) {
        AdConnect deleteAdConnect = adConnectRepository.findById(adConnectId).orElseThrow(() -> new RestControllerException(ExceptionCode.ADCONNECT_NOT_FOUND));
        adConnectRepository.delete(deleteAdConnect);
    }


    @Transactional
    public Long getTotalCommentCount(Long adConnectId) {
        AdConnect adConnect = adConnectRepository.findById(adConnectId).orElseThrow(() -> new RestControllerException(ExceptionCode.IMAGE_URL_NOT_FOUND));
        Long totalCommentCount = commentRepository.getTotalCommentCountByAdConnectId(adConnectId);
        adConnect.setCommentCount(totalCommentCount);
        adConnectRepository.save(adConnect);
        return totalCommentCount;
    }



    public AdConnect validateAdConnect(Long id) {
        Optional<AdConnect> optionalAdConnect = adConnectRepository.findById(id);
        AdConnect findAdConnect = optionalAdConnect.orElseThrow(()-> new RestControllerException(ExceptionCode.ADCONNECT_NOT_FOUND));
        return findAdConnect;
    }
}
