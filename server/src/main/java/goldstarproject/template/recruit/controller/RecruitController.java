package goldstarproject.template.recruit.controller;

import goldstarproject.template.common.dto.MultiResponseDto;
import goldstarproject.template.common.dto.SingleResponseDto;
import goldstarproject.template.recruit.dto.RecruitListDto;
import goldstarproject.template.recruit.dto.RecruitRequestDto;
import goldstarproject.template.recruit.dto.RecruitResponseDto;
import goldstarproject.template.recruit.entity.Recruit;
import goldstarproject.template.recruit.mapper.RecruitListResponseMapper;
import goldstarproject.template.recruit.mapper.RecruitRequestMapper;
import goldstarproject.template.recruit.mapper.RecruitResponseMapper;
import goldstarproject.template.recruit.service.impl.RecruitServiceImpl;
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
public class RecruitController {

    private final RecruitServiceImpl recruitService;
    private final RecruitRequestMapper recruitRequestMapper;
    private final RecruitResponseMapper recruitResponseMapper;
    private final RecruitListResponseMapper recruitListResponseMapper;


    //ROLE_ADMIN
    @PostMapping("/admin/{member-id}/recruit/insert")
    public ResponseEntity insertNotice(@PathVariable("member-id") @Positive Long memberId,
                                       @Valid @RequestBody RecruitRequestDto recruitRequestDto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        RecruitResponseDto response = recruitService.insertRecruit(recruitRequestDto,principalDetails);
        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }


    //ALL
    @GetMapping("/recruit/{recruit-id}")
    public ResponseEntity getRecruit(@PathVariable ("recruit-id") @Positive Long recruitId) {
        Recruit response = recruitService.detailRecruit(recruitId);
        return new ResponseEntity(new SingleResponseDto<>(recruitResponseMapper.toDto(response)),HttpStatus.OK);
    }


    //ROLE_ADMIN
    @PatchMapping("/admin/{member-id}/recruit/{recruit-id}/update")
    public ResponseEntity updateNotice(@PathVariable("recruit-id") @Positive Long recruitId,
                                       @PathVariable("member-id") @Positive Long memberId,
                                       @Valid @RequestBody RecruitRequestDto recruitRequestDto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        RecruitResponseDto response = recruitService.updateRecruit(recruitId,recruitRequestDto,principalDetails);
        return new ResponseEntity<>(new SingleResponseDto<>((response)), HttpStatus.OK);
    }


    // ALL
    @GetMapping("/recruits")
    public ResponseEntity noticeList(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {
        Page<RecruitListDto> pages = recruitService.findAllRecruit(page - 1, size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(),pages),HttpStatus.OK);
    }


    //게시물 제목으로 검색
    @GetMapping("/search/recruit")
    public ResponseEntity<Page<RecruitListDto>> searchRecruitTitle(@RequestParam("keyword") String keyword,
                                                                   @RequestParam("page") int page,
                                                                   @RequestParam("size") int size) {
        Page<RecruitListDto> pages = recruitService.searchRecruitList(keyword,page,size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(),pages),HttpStatus.OK);
    }


    //작성자 유저네임으로 검색
    @GetMapping("/search/recruits/username")
    public ResponseEntity<Page<RecruitListDto>> searchRecruitWriter(@RequestParam("username") String username,
                                                                      @RequestParam("page") int page,
                                                                      @RequestParam("size") int size) {
        Page<RecruitListDto> pages = recruitService.searchRecruitWriter(username,page,size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(),pages),HttpStatus.OK);
    }


    //ROLE_ADMIN
    @DeleteMapping("/admin/{member-id}/recruit/{recruit-id}/delete")
    public ResponseEntity deleteNotice(@PathVariable("recruit-id") @Positive Long recruitId,
                                       @PathVariable("member-id") @Positive Long memberId, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        recruitService.deleteRecruit(recruitId,principalDetails);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}