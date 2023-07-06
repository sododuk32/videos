package goldstarproject.template.question.service.impl;

import goldstarproject.template.common.exception.ExceptionCode;
import goldstarproject.template.common.exception.RestControllerException;
import goldstarproject.template.community.board.dto.BoardResponseDto;
import goldstarproject.template.community.board.entity.Board;
import goldstarproject.template.community.comment.repository.CommentRepository;
import goldstarproject.template.notice.entity.Notice;
import goldstarproject.template.question.dto.QuestionListDto;
import goldstarproject.template.question.dto.QuestionRequestDto;
import goldstarproject.template.question.dto.QuestionResponseDto;
import goldstarproject.template.question.entity.Question;
import goldstarproject.template.question.mapper.QuestionListResponseMapper;
import goldstarproject.template.question.mapper.QuestionRequestMapper;
import goldstarproject.template.question.repository.QuestionRepository;
import goldstarproject.template.question.service.QuestionService;
import goldstarproject.template.member.entity.Member;
import goldstarproject.template.member.repository.MemberRepository;
import goldstarproject.template.member.service.MemberService;
import goldstarproject.template.question.mapper.QuestionResponseMapper;
import goldstarproject.template.recruit.dto.RecruitListDto;
import goldstarproject.template.recruit.entity.Recruit;
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
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final QuestionRequestMapper questionRequestMapper;
    private final QuestionResponseMapper questionResponseMapper;
    private final MemberService memberService;
    private final QuestionListResponseMapper questionListResponseMapper;


    @Transactional
    public QuestionResponseDto insertQuestion(QuestionRequestDto questionRequestDto, PrincipalDetails principalDetails) {
        Member member = memberService.validateMember(principalDetails.getMember().getId());
        Question question = questionRequestMapper.toEntity(questionRequestDto);
        question.setWriter(member);
        question.setCreatedAt(LocalDateTime.now());

        if (questionRequestDto.isSecret()) {
            String password = questionRequestDto.getPassword();
            if (password == null || password.isEmpty()) {
                throw new IllegalArgumentException("비밀글 설정 시 비밀번호를 입력해주쉐요.");
            }
            question.setPassword(password);
        }
        question.setSecret(questionRequestDto.isSecret());
        Question savedQuestion = questionRepository.save(question);
        return questionResponseMapper.toDto(savedQuestion);
    }


    @Transactional
    public QuestionResponseDto updateQuestion(Long questionId, QuestionRequestDto questionRequestDto, PrincipalDetails principalDetails) {
        Member member = memberService.validateMember(principalDetails.getMember().getId());
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new RestControllerException(ExceptionCode.QUESTION_NOT_FOUND));
        Optional.ofNullable(questionRequestDto.getTitle()).ifPresent(question::setTitle);
        Optional.ofNullable(questionRequestDto.getContent()).ifPresent(question::setContent);
        question.setWriter(member);
        question.setUpdatedAt(LocalDateTime.now());
        question.getViewCount();
        if (questionRequestDto.getPassword() != null && questionRequestDto.getPassword().equals(question.getPassword())) {
            question.setSecret(questionRequestDto.isSecret());
        }
        getTotalCommentCount(questionId);
        Question savedQuestion = questionRepository.save(question);
        return questionResponseMapper.toDto(savedQuestion);
    }


    //단건의 게시글만 조회 (댓글 미포함)
    @Transactional
    public QuestionResponseDto detailQuestion(Long questionId, String password, PrincipalDetails principalDetails) {
        Question question = validateQuestion(questionId);
        Long commentCount = getTotalCommentCount(question.getId());
        question.setCommentCount(commentCount.longValue());
        getViewCount(question.getId());
        return questionResponseMapper.toDto(question);
    }
    public boolean checkPassword(Long questionId, String password) {
        Question question = validateQuestion(questionId);
        if (question == null || question.getPassword() == null || !question.getPassword().equals(password)) {
            throw new RestControllerException(ExceptionCode.PASSWORDS_DO_NOT_MATCH);
        }
        return true;
    }

    public boolean isQuestionSecret(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new RestControllerException(ExceptionCode.QUESTION_NOT_FOUND));
        if (question != null) {
            return question.isSecret();
        } else {
            return false;
        }
    }



    @Transactional(readOnly = true)
    public Page<QuestionListDto> findAllQuestion(int page, int size) {
        Page<Question> questionPage = questionRepository.findAll(PageRequest.of(
                page, size, Sort.by("id").descending()));
        return questionPage.map(questionListResponseMapper::toDto);
    }


    //게시물 제목으로 검색
    public Page<QuestionListDto> searchQuestionList(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1,size,Sort.unsorted());

        Page<Question> questionsPage = questionRepository.findByQuestionContaining(keyword,pageable);

        List<QuestionListDto> questionList = questionsPage.getContent().stream()
                .map(questionListResponseMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(questionList, pageable,questionsPage.getTotalElements());
    }


    //게시물 작성자로 검색
    public Page<QuestionListDto> searchQuestionWriter(String username,int page, int size) {
        Pageable pageable = PageRequest.of(page -1,size,Sort.unsorted());

        Page<Question> questionPage = questionRepository.findByQuestionWriter(username,pageable);

        List<QuestionListDto> questionList = questionPage.getContent().stream()
                .map(questionListResponseMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(questionList,pageable,questionPage.getTotalElements());
    }



    @Transactional
    public void deleteQuestion(Long questionId,PrincipalDetails principalDetails) {
        Member member = memberService.validateMember(principalDetails.getMember().getId());
        Question deleteQuestion = questionRepository.findById(questionId).orElseThrow(
                () -> new RestControllerException(ExceptionCode.QUESTION_NOT_FOUND));
        questionRepository.delete(deleteQuestion);
    }


    @Transactional
    public void getViewCount(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        int totalViews = question.getViewCount();
        question.setViewCount(totalViews + 1);
        questionRepository.save(question);
    }


    @Transactional
    public Long getTotalCommentCount(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        long totalCommentCount = commentRepository.getTotalCommentCountByQuestionId(questionId).intValue();
        question.setCommentCount(totalCommentCount);
        questionRepository.save(question);
        return totalCommentCount;
    }


    public Question validateQuestion(Long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        Question findQuestion = optionalQuestion.orElseThrow(()-> new RestControllerException(ExceptionCode.QUESTION_NOT_FOUND));
        return findQuestion;
    }

}