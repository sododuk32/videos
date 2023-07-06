package goldstarproject.template.storage.image_storage_02.service.impl;

import goldstarproject.template.storage.image_storage_01.entity.ImageStorage;
import goldstarproject.template.storage.image_storage_02.dto.ImageUrlStorageListDto;
import goldstarproject.template.storage.image_storage_02.dto.ImageUrlStorageRequestDto;
import goldstarproject.template.storage.image_storage_02.dto.ImageUrlStorageResponseDto;
import goldstarproject.template.storage.image_storage_02.entity.ImageUrlStorage;
import goldstarproject.template.storage.image_storage_02.mapper.ImageUrlStorageListResponseMapper;
import goldstarproject.template.storage.image_storage_02.mapper.ImageUrlStorageRequestMapper;
import goldstarproject.template.storage.image_storage_02.mapper.ImageUrlStorageResponseMapper;
import goldstarproject.template.storage.image_storage_02.repository.ImageUrlStorageRepository;
import goldstarproject.template.storage.image_storage_02.service.ImageUrlStorageService;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageUrlStorageServiceImpl implements ImageUrlStorageService {
    private final MemberService memberService;
    private final ImageUrlStorageRepository imageUrlStorageRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ImageUrlStorageRequestMapper imageUrlStorageRequestMapper;
    private final ImageUrlStorageResponseMapper imageUrlStorageResponseMapper;
    private final ImageUrlStorageListResponseMapper imageUrlStorageListResponseMapper;




    @Transactional
    public ImageUrlStorageResponseDto insertImageUrl(ImageUrlStorageRequestDto imageUrlStorageRequestDto, PrincipalDetails principalDetails) {
        Member member = memberService.detailMember(principalDetails.getMember().getId());
        ImageUrlStorage imageUrl = imageUrlStorageRequestMapper.toEntity(imageUrlStorageRequestDto);
        imageUrl.setWriter(member);
        imageUrl.setCreatedAt(LocalDateTime.now());
        ImageUrlStorage savedImageUrl = imageUrlStorageRepository.save(imageUrl);
        return imageUrlStorageResponseMapper.toDto(savedImageUrl);
    }



    @Transactional
    public ImageUrlStorageResponseDto updateImageUrl(Long imagesId, ImageUrlStorageRequestDto imageUrlStorageRequestDto, PrincipalDetails principalDetails) {
        ImageUrlStorage images = imageUrlStorageRepository.findById(imagesId).orElseThrow(() -> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        Member member = memberService.detailMember(principalDetails.getMember().getId());
        imageUrlStorageRequestMapper.toEntity(imageUrlStorageRequestDto);
        Optional.ofNullable(imageUrlStorageRequestDto.getTitle()).ifPresent(images::setTitle);
        Optional.ofNullable(imageUrlStorageRequestDto.getContent()).ifPresent(images::setContent);
        Optional.ofNullable(imageUrlStorageRequestDto.getImageContent()).ifPresent(images::setImageContent);
        images.setUpdatedAt(LocalDateTime.now());
        images.setWriter(member);
        images.getViewCount();
        getTotalCommentCount(imagesId);
        ImageUrlStorage savedImageUrl = imageUrlStorageRepository.save(images);
        return imageUrlStorageResponseMapper.toDto(savedImageUrl);
    }



    //단건의 게시글만 조회 (댓글 미포함)
    @Transactional
    public ImageUrlStorage detailImageUrl(Long id) {
        ImageUrlStorage imageUrl = validateImageUrl(id);
        Long commentCount = getTotalCommentCount(imageUrl.getId());
        imageUrl.setCommentCount(commentCount.longValue());
        getViewCount(imageUrl.getId());
        return imageUrl;
    }



    @Transactional(readOnly = true)
    public Page<ImageUrlStorageListDto> findAllImageUrl(int page, int size) {
        Page<ImageUrlStorage> imagesPage = imageUrlStorageRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
        return imagesPage.map(imageUrlStorageListResponseMapper::toDto);
    }




    //게시물 제목으로 검색
    public Page<ImageUrlStorageListDto> searchImageUrlList(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1,size,Sort.unsorted());

        Page<ImageUrlStorage> imageUrlPage = imageUrlStorageRepository.findByImagesContaining(keyword,pageable);

        List<ImageUrlStorageListDto> imageUrlList = imageUrlPage.getContent().stream()
                .map(imageUrlStorageListResponseMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(imageUrlList, pageable,imageUrlPage.getTotalElements());
    }


    //게시물 작성자로 검색
    public Page<ImageUrlStorageListDto> searchImageUrlWriter(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page -1,size,Sort.unsorted());

        Page<ImageUrlStorage> imageUrlPage = imageUrlStorageRepository.findByImagesWriter(username,pageable);

        List<ImageUrlStorageListDto> imageUrlList = imageUrlPage.getContent().stream()
                .map(imageUrlStorageListResponseMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(imageUrlList,pageable,imageUrlPage.getTotalElements());
    }



    @Transactional
    public void deleteImageUrl(Long imagesId,PrincipalDetails principalDetails) {
        ImageUrlStorage deleteImageUrl = imageUrlStorageRepository.findById(imagesId).orElseThrow(
                () -> new RestControllerException(ExceptionCode.IMAGE_URL_NOT_FOUND));
        imageUrlStorageRepository.delete(deleteImageUrl);
    }


    @Transactional
    public void getViewCount(Long imagesId) {
        ImageUrlStorage imageUrl = imageUrlStorageRepository.findById(imagesId).orElseThrow(() -> new RestControllerException(ExceptionCode.IMAGE_URL_NOT_FOUND));
        int totalViews = imageUrl.getViewCount();
        imageUrl.setViewCount(totalViews + 1);
        imageUrlStorageRepository.save(imageUrl);
    }



    @Transactional
    public Long getTotalCommentCount(Long imagesId) {
        ImageUrlStorage imageUrl = imageUrlStorageRepository.findById(imagesId).orElseThrow(() -> new RestControllerException(ExceptionCode.IMAGE_URL_NOT_FOUND));
        Long totalCommentCount = commentRepository.getTotalCommentCountByImagesId(imagesId);
        imageUrl.setCommentCount(totalCommentCount);
        imageUrlStorageRepository.save(imageUrl);
        return totalCommentCount;
    }



    public ImageUrlStorage validateImageUrl(Long id) {
        Optional<ImageUrlStorage> optionalImageUrl = imageUrlStorageRepository.findById(id);
        ImageUrlStorage findImageUrl = optionalImageUrl.orElseThrow(()-> new RestControllerException(ExceptionCode.IMAGE_URL_NOT_FOUND));
        return findImageUrl;
    }
}
