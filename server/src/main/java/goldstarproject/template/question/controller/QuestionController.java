package goldstarproject.template.question.controller;


import goldstarproject.template.adconnect.dto.AdConnectListDto;
import goldstarproject.template.common.dto.MultiResponseDto;
import goldstarproject.template.common.dto.SingleResponseDto;
import goldstarproject.template.community.board.dto.BoardResponseDto;
import goldstarproject.template.member.entity.Member;
import goldstarproject.template.question.dto.QuestionListDto;
import goldstarproject.template.question.dto.QuestionResponseDto;
import goldstarproject.template.question.entity.Question;
import goldstarproject.template.question.mapper.QuestionRequestMapper;
import goldstarproject.template.question.dto.QuestionRequestDto;
import goldstarproject.template.question.mapper.QuestionListResponseMapper;
import goldstarproject.template.question.mapper.QuestionResponseMapper;
import goldstarproject.template.question.service.impl.QuestionServiceImpl;
import goldstarproject.template.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class QuestionController {

    private final QuestionServiceImpl questionService;
    private final QuestionResponseMapper questionResponseMapper;
    private final QuestionRequestMapper questionRequestMapper;
    private final QuestionListResponseMapper questionListResponseMapper;

    // [ Fix ]모든 게시판의 삭제 기능은 Admin 이 가지고 있어야한다.


    //MEMBER
    @PostMapping("/member/{member-id}/question/insert")
    public ResponseEntity insertQuestion(@PathVariable("member-id") @Positive Long memberId,
                                         @Valid @RequestBody QuestionRequestDto questionRequestDto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        QuestionResponseDto response = questionService.insertQuestion(questionRequestDto, principalDetails);
        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.OK);
    }


    @PatchMapping("/member/{member-id}/question/{question-id}/update")
    public ResponseEntity updateQuestion(@PathVariable("member-id") @Positive Long memberId,
                                         @PathVariable("question-id") @Positive Long questionId,
                                         @Valid @RequestBody QuestionRequestDto questionRequestDto,
                                         Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        QuestionResponseDto response = questionService.updateQuestion(questionId, questionRequestDto, principalDetails);
        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.OK);
    }


    //boolean 비밀글 여부에 따라 비밀번호 입력 시 게시물 조회
    @GetMapping("/member/question/{question-id}")
    public ResponseEntity<?> getBoard(@PathVariable("question-id") @Positive Long questionId,
                                      @RequestParam(required = false) String password,
                                      Authentication authentication) {

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String role = principalDetails.getMember().getRole();

        QuestionResponseDto response;

        if ("ROLE_ADMIN".equals(role)) {
            response = questionService.detailQuestion(questionId, null, principalDetails);
            return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
        } else if ("ROLE_MEMBER".equals(role)) {
            boolean isSecret = questionService.isQuestionSecret(questionId);
            if (isSecret && (password == null || !questionService.checkPassword(questionId, password))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            response = questionService.detailQuestion(questionId, password, principalDetails);
            return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
        } else {
            response = questionService.detailQuestion(questionId, null, principalDetails);
            return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
        }
    }



    //페이지네이션
    @GetMapping("/questions")
    public ResponseEntity getQuestions(@Positive @RequestParam int page,
                                       @Positive @RequestParam int size) {
        Page<QuestionListDto> pages = questionService.findAllQuestion(page - 1, size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(), pages), HttpStatus.OK);
    }



    //게시물 제목으로 검색
    @GetMapping("/search/questions")
    public ResponseEntity<Page<QuestionListDto>> searchQuestionTitle(@RequestParam("keyword") String keyword,
                                                                     @RequestParam("page") int page,
                                                                     @RequestParam("size") int size) {
        Page<QuestionListDto> pages = questionService.searchQuestionList(keyword,page,size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(), pages), HttpStatus.OK);
    }



    //작성자 유저네임으로 검색
    @GetMapping("/search/questions/username")
    public ResponseEntity<Page<QuestionListDto>> searchQuestionWriter(@RequestParam("username") String username,
                                                                        @RequestParam("page") int page,
                                                                        @RequestParam("size") int size) {
        Page<QuestionListDto> pages = questionService.searchQuestionWriter(username,page,size);
        return new ResponseEntity(new MultiResponseDto<>(pages.getContent(), pages), HttpStatus.OK);
    }



    @DeleteMapping("/member/{member-id}/question/{question-id}/delete")
    public ResponseEntity deleteQuestion(@PathVariable("question-id") @Positive Long questionId,
                                       @PathVariable("member-id") @Positive Long memberId,
                                       Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        questionService.deleteQuestion(questionId,principalDetails);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
