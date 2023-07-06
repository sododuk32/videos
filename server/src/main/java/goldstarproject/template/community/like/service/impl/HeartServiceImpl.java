package goldstarproject.template.community.like.service.impl;

import goldstarproject.template.common.exception.ExceptionCode;
import goldstarproject.template.common.exception.RestControllerException;
import goldstarproject.template.community.board.entity.Board;
import goldstarproject.template.community.board.repository.BoardRepository;
import goldstarproject.template.community.like.dto.HeartRequestDto;
import goldstarproject.template.community.like.entity.Heart;
import goldstarproject.template.community.like.repository.HeartRepository;
import goldstarproject.template.community.like.service.HeartService;
import goldstarproject.template.question.entity.Question;
import goldstarproject.template.question.repository.QuestionRepository;
import goldstarproject.template.member.entity.Member;
import goldstarproject.template.member.repository.MemberRepository;
import goldstarproject.template.notice.entity.Notice;
import goldstarproject.template.notice.repository.NoticeRepository;
import goldstarproject.template.security.auth.PrincipalDetails;
import goldstarproject.template.storage.image_storage_01.entity.ImageStorage;
import goldstarproject.template.storage.image_storage_01.repository.ImageStorageRepository;
import goldstarproject.template.storage.image_storage_02.entity.ImageUrlStorage;
import goldstarproject.template.storage.image_storage_02.repository.ImageUrlStorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService {
    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final NoticeRepository noticeRepository;
    private final QuestionRepository questionRepository;
    private final ImageStorageRepository imageStorageRepository;
    private final ImageUrlStorageRepository imageUrlStorageRepository;



    @Override
    public void boardInsertHeart(HeartRequestDto heartRequestDto, PrincipalDetails principalDetails) throws Exception {
        Member member = memberRepository.findById(principalDetails.getMember().getId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        Board board = boardRepository.findById(heartRequestDto.getBoardId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));

        if (heartRepository.findByMemberAndBoard(member, board).isPresent()) {
            throw new Exception();
        }
        Heart heart = Heart.builder()
                .board(board)
                .member(member)
                .build();

        boardRepository.updateBoardHeartCount(board,true);
        heartRepository.save(heart);
    }

    @Override
    public void boardDeleteHeart(HeartRequestDto heartRequestDto,PrincipalDetails principalDetails) throws Exception {
        Member member = memberRepository.findById(principalDetails.getMember().getId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        Board board = boardRepository.findById(heartRequestDto.getBoardId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        Heart heart = heartRepository.findByMemberAndBoard(member, board)
                .orElseThrow(()-> new RestControllerException(ExceptionCode.HEART_NOT_FOUND));
        boardRepository.updateBoardHeartCount(board,false);
        heartRepository.delete(heart);
    }



    @Override
    public void noticeInsertHeart(HeartRequestDto heartRequestDto,PrincipalDetails principalDetails) throws Exception {
        Member member = memberRepository.findById(principalDetails.getMember().getId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        Notice notice = noticeRepository.findById(heartRequestDto.getBoardId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));

        if (heartRepository.findByMemberAndNotice(member, notice).isPresent()) {
            throw new Exception();
        }
        Heart heart = Heart.builder()
                .notice(notice)
                .member(member)
                .build();

        noticeRepository.updateNoticeHeartCount(notice,true);
        heartRepository.save(heart);

    }

    @Override
    public void noticeDeleteHeart(HeartRequestDto heartRequestDto,PrincipalDetails principalDetails) throws Exception{
        Member member = memberRepository.findById(principalDetails.getMember().getId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        Notice notice = noticeRepository.findById(heartRequestDto.getBoardId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        Heart heart = heartRepository.findByMemberAndNotice(member, notice)
                .orElseThrow(()-> new RestControllerException(ExceptionCode.HEART_NOT_FOUND));
        noticeRepository.updateNoticeHeartCount(notice,false);
        heartRepository.delete(heart);
    }

    @Override
    public void questionInsertHeart(HeartRequestDto heartRequestDto,PrincipalDetails principalDetails) throws Exception {
        Member member = memberRepository.findById(principalDetails.getMember().getId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        Question question  = questionRepository.findById(heartRequestDto.getQuestionId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.QUESTION_NOT_FOUND));
        if (heartRepository.findByMemberAndQuestion(member, question).isPresent()) {
            throw new Exception();
        }
        Heart heart = Heart.builder()
                .question(question)
                .member(member)
                .build();
        questionRepository.updateNoticeHeartCount(question,true);
        heartRepository.save(heart);

    }

    @Override
    public void questionDeleteHeart(HeartRequestDto heartRequestDto,PrincipalDetails principalDetails) throws Exception {
        Member member = memberRepository.findById(principalDetails.getMember().getId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        Question question = questionRepository.findById(heartRequestDto.getQuestionId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.BOARD_NOT_FOUND));
        Heart heart = heartRepository.findByMemberAndQuestion(member, question)
                .orElseThrow(()-> new RestControllerException(ExceptionCode.HEART_NOT_FOUND));
        questionRepository.updateNoticeHeartCount(question,false);
        heartRepository.delete(heart);
    }


    @Override
    public void imageInsertHeart(HeartRequestDto heartRequestDto,PrincipalDetails principalDetails) throws Exception {
        Member member = memberRepository.findById(principalDetails.getMember().getId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        ImageStorage image  = imageStorageRepository.findById(heartRequestDto.getImageId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.IMAGE_NOT_FOUND));
        if (heartRepository.findByMemberAndImage(member, image).isPresent()) {
            throw new Exception();
        }
        Heart heart = Heart.builder()
                .image(image)
                .member(member)
                .build();
        imageStorageRepository.updateImageHeartCount(image,true);
        heartRepository.save(heart);
    }

    @Override
    public void imageDeleteHeart(HeartRequestDto heartRequestDto,PrincipalDetails principalDetails) throws Exception {
        Member member = memberRepository.findById(principalDetails.getMember().getId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        ImageStorage image = imageStorageRepository.findById(heartRequestDto.getImageId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.IMAGE_NOT_FOUND));
        Heart heart = heartRepository.findByMemberAndImage(member, image)
                .orElseThrow(()-> new RestControllerException(ExceptionCode.HEART_NOT_FOUND));
        imageStorageRepository.updateImageHeartCount(image,false);
        heartRepository.delete(heart);
    }





    @Override
    public void imagesInsertHeart(HeartRequestDto heartRequestDto,PrincipalDetails principalDetails) throws Exception {
        Member member = memberRepository.findById(principalDetails.getMember().getId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        ImageUrlStorage images  = imageUrlStorageRepository.findById(heartRequestDto.getImagesId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.IMAGE_URL_NOT_FOUND));
        if (heartRepository.findByMemberAndImages(member, images).isPresent()) {
            throw new Exception();
        }
        Heart heart = Heart.builder()
                .images(images)
                .member(member)
                .build();
        imageUrlStorageRepository.updateImagesHeartCount(images,true);
        heartRepository.save(heart);
    }

    @Override
    public void imagesDeleteHeart(HeartRequestDto heartRequestDto,PrincipalDetails principalDetails) throws Exception {
        Member member = memberRepository.findById(principalDetails.getMember().getId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.MEMBER_NOT_FOUND));
        ImageUrlStorage images = imageUrlStorageRepository.findById(heartRequestDto.getImagesId())
                .orElseThrow(()-> new RestControllerException(ExceptionCode.IMAGE_URL_NOT_FOUND));
        Heart heart = heartRepository.findByMemberAndImages(member, images)
                .orElseThrow(()-> new RestControllerException(ExceptionCode.HEART_NOT_FOUND));
        imageUrlStorageRepository.updateImagesHeartCount(images,false);
        heartRepository.delete(heart);
    }



}
