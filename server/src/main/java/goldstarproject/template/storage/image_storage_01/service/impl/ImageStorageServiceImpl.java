package goldstarproject.template.storage.image_storage_01.service.impl;

import goldstarproject.template.adconnect.entity.AdConnect;
import goldstarproject.template.storage.image_storage_01.dto.ImageStorageListDto;
import goldstarproject.template.storage.image_storage_01.dto.ImageStorageRequestDto;
import goldstarproject.template.storage.image_storage_01.dto.ImageStorageResponseDto;
import goldstarproject.template.storage.image_storage_01.entity.ImageStorage;
import goldstarproject.template.storage.image_storage_01.mapper.ImageStorageListResponseMapper;
import goldstarproject.template.storage.image_storage_01.mapper.ImageStorageRequestMapper;
import goldstarproject.template.storage.image_storage_01.mapper.ImageStorageResponseMapper;
import goldstarproject.template.storage.image_storage_01.repository.ImageStorageRepository;
import goldstarproject.template.storage.image_storage_01.service.ImageStorageService;
import goldstarproject.template.common.exception.ExceptionCode;
import goldstarproject.template.common.exception.RestControllerException;
import goldstarproject.template.community.comment.repository.CommentRepository;
import goldstarproject.template.member.dto.MemberProfileDto;
import goldstarproject.template.member.entity.Member;
import goldstarproject.template.member.repository.MemberRepository;
import goldstarproject.template.member.service.MemberService;
import goldstarproject.template.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageStorageServiceImpl implements ImageStorageService {
    private final MemberService memberService;
    private final ImageStorageRepository imageStorageRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ImageStorageRequestMapper imageStorageRequestMapper;
    private final ImageStorageResponseMapper imageStorageResponseMapper;
    private final ImageStorageListResponseMapper imageStorageListResponseMapper;




    public ImageStorageResponseDto insertImage(ImageStorageRequestDto imageStorageRequestDto, PrincipalDetails principalDetails) {
        Member member = memberService.detailMember(principalDetails.getMember().getId());
        ImageStorage image = imageStorageRequestMapper.toEntity(imageStorageRequestDto);
        image.setWriter(member);
        image.setCreatedAt(LocalDateTime.now());
        ImageStorage savedImageUrl = imageStorageRepository.save(image);
        return imageStorageResponseMapper.toDto(savedImageUrl);
    }



    @Transactional
    public ImageStorageResponseDto updateImage(Long imageId, ImageStorageRequestDto imageStorageRequestDto, PrincipalDetails principalDetails) {
        ImageStorage image = imageStorageRepository.findById(imageId).orElseThrow(() -> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        Member member = memberService.detailMember(principalDetails.getMember().getId());
        imageStorageRequestMapper.toEntity(imageStorageRequestDto);
        Optional.ofNullable(imageStorageRequestDto.getTitle()).ifPresent(image::setTitle);
        Optional.ofNullable(imageStorageRequestDto.getContent()).ifPresent(image::setContent);
        Optional.ofNullable(imageStorageRequestDto.getImageContent()).ifPresent(image::setImageContent);
        image.setUpdatedAt(LocalDateTime.now());
        image.setWriter(member);
        image.getViewCount();
        getTotalCommentCount(imageId);
        ImageStorage savedImageUrl = imageStorageRepository.save(image);
        return imageStorageResponseMapper.toDto(savedImageUrl);
    }



    //단건의 게시글만 조회 (댓글 미포함)
    @Transactional
    public ImageStorage detailImage(Long id) {
        ImageStorage image = validateImage(id);
        Long commentCount = getTotalCommentCount(image.getId());
        image.setCommentCount(commentCount.longValue());
        getViewCount(image.getId());
        return image;
    }





    @Transactional(readOnly = true)
    public Page<ImageStorageListDto> findAllImage(int page, int size) {
         Page<ImageStorage> imagePage = imageStorageRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
         return imagePage.map(imageStorageListResponseMapper::toDto);
    }



    //게시물 제목으로 검색
    public Page<ImageStorageListDto> searchImageList(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1,size,Sort.unsorted());

        Page<ImageStorage> imagePage = imageStorageRepository.findByImageContaining(keyword,pageable);

        List<ImageStorageListDto> imageList = imagePage.getContent().stream()
                .map(imageStorageListResponseMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(imageList, pageable,imagePage.getTotalElements());
    }


    //게시물 작성자로 검색
    public Page<ImageStorageListDto> searchImageWriter(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page -1,size,Sort.unsorted());

        Page<ImageStorage> imagePage = imageStorageRepository.findByImageWriter(username,pageable);

        List<ImageStorageListDto> imageList = imagePage.getContent().stream()
                .map(imageStorageListResponseMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(imageList,pageable,imagePage.getTotalElements());
    }

    @Transactional
    public void deleteImage(Long imageId,PrincipalDetails principalDetails) {
        ImageStorage deleteImage = imageStorageRepository.findById(imageId).orElseThrow(
                () -> new RestControllerException(ExceptionCode.NOTICE_NOT_FOUND));
        imageStorageRepository.delete(deleteImage);
    }



    @Transactional
    public void getViewCount(Long imageId) {
        ImageStorage image = imageStorageRepository.findById(imageId).orElseThrow(() -> new RestControllerException(ExceptionCode.IMAGE_NOT_FOUND));
        int totalViews = image.getViewCount();
        image.setViewCount(totalViews + 1);
        imageStorageRepository.save(image);
    }



    @Transactional
    public Long getTotalCommentCount(Long imageId) {
        ImageStorage image = imageStorageRepository.findById(imageId).orElseThrow(() -> new RestControllerException(ExceptionCode.IMAGE_URL_NOT_FOUND));
        Long totalCommentCount = commentRepository.getTotalCommentCountByImageId(imageId);
        image.setCommentCount(totalCommentCount);
        imageStorageRepository.save(image);
        return totalCommentCount;
    }


    public ImageStorage validateImage(Long id) {
        Optional<ImageStorage> optionalImage = imageStorageRepository.findById(id);
        ImageStorage findImage = optionalImage.orElseThrow(()-> new RestControllerException(ExceptionCode.IMAGE_NOT_FOUND));
        return findImage;
    }
}
