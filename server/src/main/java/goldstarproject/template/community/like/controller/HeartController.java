package goldstarproject.template.community.like.controller;


import goldstarproject.template.community.like.dto.HeartRequestDto;
import goldstarproject.template.community.like.service.impl.HeartServiceImpl;
import goldstarproject.template.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HeartController {

    private final HeartServiceImpl heartService;


    @PostMapping("/member/{board-type}/{board-id}/heart/insert")
    public ResponseEntity insertHeart(@PathVariable("board-type") String boardType,
                                      @Valid @RequestBody HeartRequestDto heartRequestDto,
                                      Authentication authentication) throws Exception {
        if (boardType.equals("board")) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            heartService.boardInsertHeart(heartRequestDto,principalDetails);
        } else if (boardType.equals("notice")) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            heartService.noticeInsertHeart(heartRequestDto,principalDetails);
        } else if (boardType.equals("question")) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            heartService.questionInsertHeart(heartRequestDto,principalDetails);
        } else if (boardType.equals("image")) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            heartService.imageInsertHeart(heartRequestDto,principalDetails);
        } else if (boardType.equals("images")) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            heartService.imagesInsertHeart(heartRequestDto,principalDetails);
        } return new ResponseEntity(HttpStatus.OK);
    }


    @DeleteMapping("/member/{board-type}/{board-id}/heart/delete")
    public ResponseEntity DeleteHeart(@PathVariable("board-type") String boardType,
                                      @PathVariable("board-id") @Positive Long boardId,
                                      @Valid @RequestBody HeartRequestDto heartRequestDto,
                                      Authentication authentication) throws Exception {
        if (boardType.equals("board")) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            heartService.boardDeleteHeart(heartRequestDto,principalDetails);
        } else if (boardType.equals("notice")) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            heartService.noticeDeleteHeart(heartRequestDto,principalDetails);
        } else if (boardType.equals("question")) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            heartService.questionDeleteHeart(heartRequestDto,principalDetails);
        } else if (boardType.equals("image")) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            heartService.imageDeleteHeart(heartRequestDto,principalDetails);
        } else if (boardType.equals("images")) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            heartService.imagesDeleteHeart(heartRequestDto,principalDetails);
        } return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

