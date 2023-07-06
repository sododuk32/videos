package goldstarproject.template.notice.controller;

import goldstarproject.template.common.dto.MultiResponseDto;
import goldstarproject.template.common.dto.SingleResponseDto;


import goldstarproject.template.notice.dto.NoticeListDto;
import goldstarproject.template.notice.dto.NoticeRequestDto;
import goldstarproject.template.notice.dto.NoticeResponseDto;
import goldstarproject.template.notice.entity.Notice;
import goldstarproject.template.notice.mapper.NoticeListResponseMapper;
import goldstarproject.template.notice.mapper.NoticeResponseMapper;
import goldstarproject.template.notice.service.impl.NoticeServiceImpl;
import goldstarproject.template.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class NoticeController {

    private final NoticeServiceImpl noticeService;
    private final NoticeResponseMapper noticeResponseMapper;
    private final NoticeListResponseMapper noticeListResponseMapper;


    //ROLE_ADMIN
    @PostMapping("/admin/{member-id}/notice/insert")
    public ResponseEntity insertNotice(@PathVariable("member-id") @Positive Long memberId,
                                       @Valid @RequestBody NoticeRequestDto noticeRequestDto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        NoticeResponseDto response = noticeService.insertNotice(noticeRequestDto,principalDetails);
        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }



    @GetMapping("/notice/{notice-id}")
    public ResponseEntity getNotice(@PathVariable ("notice-id") @Positive Long noticeId) {
        Notice response = noticeService.detailNotice(noticeId);
        return new ResponseEntity(new SingleResponseDto<>(noticeResponseMapper.toDto(response)),HttpStatus.OK);
    }



    @GetMapping("/notices")
    public ResponseEntity noticeList(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size,
                                     Pageable pageable) {
        Page<NoticeListDto> noticePage = noticeService.findSortedNoticesWithPagination(page- 1,size);
        return new ResponseEntity(new MultiResponseDto<>(noticePage.getContent(),noticePage),HttpStatus.OK);
    }


    //게시물 제목으로 검색
    @GetMapping("/search/notice")
    public ResponseEntity<Page<NoticeListDto>> searchNoticeTitle(@RequestParam("keyword") String keyword,
                                                                @RequestParam("page") int page,
                                                                @RequestParam("size") int size) {
        Page<NoticeListDto> pages = noticeService.searchNoticeList(keyword,page,size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(),pages),HttpStatus.OK);
    }


    //작성자 유저네임으로 검색
    @GetMapping("/search/notice/username")
    public ResponseEntity<Page<NoticeListDto>> searchNoticeWriter(@RequestParam("username") String username,
                                                                 @RequestParam("page") int page,
                                                                 @RequestParam("size") int size) {
        Page<NoticeListDto> pages = noticeService.searchNoticeWriter(username,page,size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(),pages),HttpStatus.OK);
    }


    //ROLE_ADMIN
    @PatchMapping("/admin/notice/{notice-id}/{member-id}/update")
    public ResponseEntity updateNotice(@PathVariable("notice-id") @Positive Long noticeId,
                                       @PathVariable("member-id") @Positive Long memberId,
                                       @Valid @RequestBody NoticeRequestDto noticeRequestDto,
                                       Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        NoticeResponseDto response = noticeService.updateNotice(noticeId,noticeRequestDto,principalDetails);
        return new ResponseEntity<>(new SingleResponseDto<>((response)), HttpStatus.OK);
    }


    //ROLE_ADMIN
    @DeleteMapping("/admin/{member-id}/notice/{notice-id}/delete")
    public ResponseEntity deleteNotice(@PathVariable("notice-id") @Positive Long noticeId,
                                       @PathVariable("member-id") @Positive Long memberId,
                                       Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        noticeService.deleteNotice(noticeId,principalDetails);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
