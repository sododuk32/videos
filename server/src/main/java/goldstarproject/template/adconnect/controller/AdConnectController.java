package goldstarproject.template.adconnect.controller;

import goldstarproject.template.adconnect.dto.AdConnectListDto;
import goldstarproject.template.adconnect.dto.AdConnectRequestDto;
import goldstarproject.template.adconnect.dto.AdConnectResponseDto;
import goldstarproject.template.adconnect.entity.AdConnect;
import goldstarproject.template.adconnect.mapper.AdConnectListResponseMapper;
import goldstarproject.template.adconnect.mapper.AdConnectResponseMapper;
import goldstarproject.template.adconnect.service.AdConnectService;
import goldstarproject.template.common.dto.MultiResponseDto;
import goldstarproject.template.common.dto.SingleResponseDto;
import goldstarproject.template.notice.dto.NoticeListDto;
import goldstarproject.template.notice.dto.NoticeRequestDto;
import goldstarproject.template.notice.dto.NoticeResponseDto;
import goldstarproject.template.notice.entity.Notice;
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
public class AdConnectController { //광고문의 API


    //작성자와 어므민만 해당 게시글을 확인할 수 있어야함


    private final AdConnectService adConnectService;
    private final AdConnectResponseMapper adConnectResponseMapper;
    private final AdConnectListResponseMapper adConnectListResponseMapper;


    //MEMBER
    @PostMapping("/member/{member-id}/adConnects/insert")
    public ResponseEntity createAdConnect(@PathVariable("member-id") @Positive Long memberId,
                                          @Valid @RequestBody AdConnectRequestDto adConnectRequestDto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        AdConnectResponseDto response = adConnectService.insertAdConnect(adConnectRequestDto, principalDetails);
        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }



    //MEMBER
    @PatchMapping("/member/{member-id}/adConnects/{adConnects-id}/update")
    public ResponseEntity updateNotice(@PathVariable("adConnects-id") @Positive Long adConnectsId,
                                       @PathVariable("member-id") @Positive Long memberId,
                                       @Valid @RequestBody AdConnectRequestDto adConnectRequestDto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        AdConnectResponseDto response = adConnectService.updateAdConnect(adConnectsId, adConnectRequestDto, principalDetails);
        return new ResponseEntity<>(new SingleResponseDto<>((response)), HttpStatus.OK);
    }



    //ADMIN
    @GetMapping("/adConnects/{adConnects-id}")
    public ResponseEntity getAdConnects(@PathVariable("adConnects-id") @Positive Long adConnectsId) {
        AdConnect response = adConnectService.detailAdConnect(adConnectsId);
        return new ResponseEntity(new SingleResponseDto<>(adConnectResponseMapper.toDto(response)), HttpStatus.OK);
    }



    // ALL (목록은 모두 볼 수 있으나 특정 게시물 조회는 ADMIN 권한 필요 )
    @GetMapping("/adConnects")
    public ResponseEntity adConnectsList(@Positive @RequestParam int page,
                                         @Positive @RequestParam int size) {
        Page<AdConnectListDto> pages = adConnectService.findAllAdConnect(page - 1, size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(),pages), HttpStatus.OK);
    }



    //게시물 제목으로 검색
    @GetMapping("/search/adConnects")
    public ResponseEntity<Page<AdConnectListDto>> searchAdConnectTitle(@RequestParam("keyword") String keyword,
                                                                       @RequestParam("page") int page,
                                                                    @RequestParam("size") int size) {
        Page<AdConnectListDto> pages = adConnectService.searchAdConnectList(keyword,page,size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(), pages), HttpStatus.OK);
    }



    //작성자 유저네임으로 검색
    @GetMapping("/search/adConnects/username")
    public ResponseEntity<Page<AdConnectListDto>> searchAdConnectWriter(@RequestParam("username") String username,
                                                                  @RequestParam("page") int page,
                                                                  @RequestParam("size") int size) {
        Page<AdConnectListDto> pages = adConnectService.searchAdConnectWriter(username,page,size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(), pages), HttpStatus.OK);
    }



    //ADMIN
    @DeleteMapping("/admin/adConnects/{adConnects-id}/delete")
    public ResponseEntity deleteNotice(@PathVariable("adConnects-id") @Positive Long adConnectsId,
                                       Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        adConnectService.deleteAdConnect(adConnectsId, principalDetails);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
