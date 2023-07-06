package goldstarproject.template.storage.image_storage_03.controller;

import goldstarproject.template.common.dto.MultiResponseDto;
import goldstarproject.template.common.dto.SingleResponseDto;
import goldstarproject.template.security.auth.PrincipalDetails;
import goldstarproject.template.storage.image_storage_02.entity.ImageUrlStorage;
import goldstarproject.template.storage.image_storage_03.dto.LiveListDto;
import goldstarproject.template.storage.image_storage_03.dto.LiveRequestDto;
import goldstarproject.template.storage.image_storage_03.dto.LiveResponseDto;

import goldstarproject.template.storage.image_storage_03.entity.Live;
import goldstarproject.template.storage.image_storage_03.mapper.LiveListResponseMapper;
import goldstarproject.template.storage.image_storage_03.mapper.LiveResponseMapper;
import goldstarproject.template.storage.image_storage_03.service.impl.LiveServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LiveController {


    private final LiveServiceImpl liveService;
    private final LiveResponseMapper liveResponseMapper;
    private final LiveListResponseMapper liveListResponseMapper;


    //ROLE_ADMIN
    @PostMapping("/admin/{member-id}/live/insert")
    public ResponseEntity insertLive(@PathVariable("member-id") @Positive Long memberId,
                                       @Valid @RequestBody LiveRequestDto liveRequestDto,
                                      Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        LiveResponseDto response = liveService.insertLive(liveRequestDto,principalDetails);
        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }


    @GetMapping("/live/{live_id}")
    public ResponseEntity getImage(@PathVariable ("live_id") @Positive Long liveId) {
        Live response = liveService.detailLive(liveId);
        return new ResponseEntity<>(new SingleResponseDto<>(liveResponseMapper.toDto(response)),HttpStatus.OK);
    }


    // ALL
    @GetMapping("/lives")
    public ResponseEntity liveList(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {
        Page<LiveListDto> pages = liveService.findAllLive(page - 1, size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(),pages),HttpStatus.OK);
    }


    //ROLE_ADMIN
    @PatchMapping("/admin/{member-id}/live/{live-id}/update")
    public ResponseEntity updateImage(@PathVariable("live-id") @Positive Long liveId,
                                      @PathVariable("member-id") @Positive Long memberId,
                                      @Valid @RequestBody LiveRequestDto liveRequestDto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        LiveResponseDto response = liveService.updateLive(liveId,liveRequestDto,principalDetails);
        return new ResponseEntity<>(new SingleResponseDto<>((response)), HttpStatus.OK);
    }



    //ROLE_ADMIN
    @DeleteMapping("/admin/live/{live-id}/delete")
    public ResponseEntity deleteNotice(@PathVariable("live-id") @Positive Long liveId,
                                       Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        liveService.deleteLive(liveId,principalDetails);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
