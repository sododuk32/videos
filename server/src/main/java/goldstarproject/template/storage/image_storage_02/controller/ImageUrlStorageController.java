package goldstarproject.template.storage.image_storage_02.controller;

import goldstarproject.template.storage.image_storage_02.dto.ImageUrlStorageListDto;
import goldstarproject.template.storage.image_storage_02.dto.ImageUrlStorageRequestDto;
import goldstarproject.template.storage.image_storage_02.dto.ImageUrlStorageResponseDto;
import goldstarproject.template.storage.image_storage_02.entity.ImageUrlStorage;
import goldstarproject.template.storage.image_storage_02.mapper.ImageUrlStorageListResponseMapper;
import goldstarproject.template.storage.image_storage_02.mapper.ImageUrlStorageResponseMapper;
import goldstarproject.template.storage.image_storage_02.service.impl.ImageUrlStorageServiceImpl;
import goldstarproject.template.common.dto.MultiResponseDto;
import goldstarproject.template.common.dto.SingleResponseDto;
import goldstarproject.template.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ImageUrlStorageController {

    private final ImageUrlStorageServiceImpl imageUrlStorageService;
    private final ImageUrlStorageResponseMapper imageUrlStorageResponseMapper;
    private final ImageUrlStorageListResponseMapper imageUrlStorageListResponseMapper;


    //ROLE_ADMIN
    @PostMapping("/admin/{member-id}/images/insert")
    public ResponseEntity insertImage(@PathVariable("member-id") @Positive Long memberId,
                                      @Valid @RequestBody ImageUrlStorageRequestDto imageUrlStorageRequestDto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        ImageUrlStorageResponseDto response = imageUrlStorageService.insertImageUrl(imageUrlStorageRequestDto,principalDetails);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }



    @GetMapping("/images/{images_id}")
    public ResponseEntity getImage(@PathVariable ("images_id") @Positive Long imagesId) {
        ImageUrlStorage response = imageUrlStorageService.detailImageUrl(imagesId);
        return new ResponseEntity<>(new SingleResponseDto<>(imageUrlStorageResponseMapper.toDto(response)),HttpStatus.OK);
    }




    // ALL
    @GetMapping("/all-images")
    public ResponseEntity imageUrlList(@Positive @RequestParam int page,
                                       @Positive @RequestParam int size) {
        Page<ImageUrlStorageListDto> pages = imageUrlStorageService.findAllImageUrl(page - 1, size);
        return new ResponseEntity<>(new MultiResponseDto<>(pages.getContent(),pages),HttpStatus.OK);
    }


    //게시물 제목으로 검색
    @GetMapping("/search/images")
    public ResponseEntity<Page<ImageUrlStorageListDto>> searchImageUrlTitle(@RequestParam("keyword") String keyword,
                                                                            @RequestParam("page") int page,
                                                                            @RequestParam("size") int size) {
        Page<ImageUrlStorageListDto> pages = imageUrlStorageService.searchImageUrlList(keyword,page,size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(),pages),HttpStatus.OK);
    }


    //작성자 유저네임으로 검색
    @GetMapping("/search/images/username")
    public ResponseEntity<Page<ImageUrlStorageListDto>> searchImageUrlWriter(@RequestParam("username") String username,
                                                                             @RequestParam("page") int page,
                                                                             @RequestParam("size") int size) {
        Page<ImageUrlStorageListDto> pages = imageUrlStorageService.searchImageUrlWriter(username,page,size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(),pages),HttpStatus.OK);
    }



    //ROLE_ADMIN
    @PatchMapping("/admin/{member-id}/images/{images_id}/update")
    public ResponseEntity updateImageUrl(@PathVariable("images_id") @Positive Long imagesId,
                                         @PathVariable("member-id") @Positive Long memberId,
                                         @Valid @RequestBody ImageUrlStorageRequestDto imageUrlStorageRequestDto,
                                         Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        ImageUrlStorageResponseDto response = imageUrlStorageService.updateImageUrl(imagesId,imageUrlStorageRequestDto,principalDetails);
        return new ResponseEntity<>(new SingleResponseDto<>((response)), HttpStatus.OK);
    }


    //ROLE_ADMIN
    @DeleteMapping("/admin/images/{images_id}/delete")
    public ResponseEntity deleteNotice(@PathVariable("images_id") @Positive Long imagesId,
                                       Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        imageUrlStorageService.deleteImageUrl(imagesId,principalDetails);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
