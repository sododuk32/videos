package goldstarproject.template.storage.image_storage_01.controller;

import goldstarproject.template.storage.image_storage_01.dto.ImageStorageListDto;
import goldstarproject.template.storage.image_storage_01.dto.ImageStorageRequestDto;
import goldstarproject.template.storage.image_storage_01.dto.ImageStorageResponseDto;
import goldstarproject.template.storage.image_storage_01.entity.ImageStorage;
import goldstarproject.template.storage.image_storage_01.mapper.ImageStorageListResponseMapper;
import goldstarproject.template.storage.image_storage_01.mapper.ImageStorageResponseMapper;
import goldstarproject.template.storage.image_storage_01.service.impl.ImageStorageServiceImpl;
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
public class ImageStorageController {

    private final ImageStorageServiceImpl imageStorageService;
    private final ImageStorageResponseMapper imageStorageResponseMapper;
    private final ImageStorageListResponseMapper imageStorageListResponseMapper;


    //ROLE_ADMIN
    @PostMapping("/admin/{member-id}/image/insert")
    public ResponseEntity insertImage(@PathVariable("member-id") @Positive Long memberId,
                                       @Valid @RequestBody ImageStorageRequestDto imageStorageRequestDto,
                                      Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        ImageStorageResponseDto response = imageStorageService.insertImage(imageStorageRequestDto,principalDetails);
        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }


    @GetMapping("/image/{image-id}")
    public ResponseEntity getImage(@PathVariable ("image-id") @Positive Long imageId) {
        ImageStorage response = imageStorageService.detailImage(imageId);
        return new ResponseEntity(new SingleResponseDto<>(imageStorageResponseMapper.toDto(response)),HttpStatus.OK);
    }


    // ALL
    @GetMapping("/images")
    public ResponseEntity imageList(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {
        Page<ImageStorageListDto> pages = imageStorageService.findAllImage(page - 1, size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(),pages),HttpStatus.OK);
    }


    //게시물 제목으로 검색
    @GetMapping("/search/image")
    public ResponseEntity<Page<ImageStorageListDto>> searchImageTitle(@RequestParam("keyword") String keyword,
                                                                       @RequestParam("page") int page,
                                                                       @RequestParam("size") int size) {
        Page<ImageStorageListDto> pages = imageStorageService.searchImageList(keyword,page,size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(),pages),HttpStatus.OK);
    }


    //작성자 유저네임으로 검색
    @GetMapping("/search/image/username")
    public ResponseEntity<Page<ImageStorageListDto>> searchImageWriter(@RequestParam("username") String username,
                                                                        @RequestParam("page") int page,
                                                                        @RequestParam("size") int size) {
        Page<ImageStorageListDto> pages = imageStorageService.searchImageWriter(username,page,size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(),pages),HttpStatus.OK);
    }


    //ROLE_ADMIN
    @PatchMapping("/admin/{member-id}/image/{image-id}/update")
    public ResponseEntity updateImage(@PathVariable("image-id") @Positive Long imageId,
                                       @PathVariable("member-id") @Positive Long memberId,
                                       @Valid @RequestBody ImageStorageRequestDto imageStorageRequestDto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        ImageStorageResponseDto response = imageStorageService.updateImage(imageId,imageStorageRequestDto,principalDetails);
        return new ResponseEntity<>(new SingleResponseDto<>((response)), HttpStatus.OK);
    }



    //ROLE_ADMIN
    @DeleteMapping("/admin/image/{image-id}/delete")
    public ResponseEntity deleteNotice(@PathVariable("image-id") @Positive Long imageId,
                                       Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        imageStorageService.deleteImage(imageId,principalDetails);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
