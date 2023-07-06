package goldstarproject.template.community.like.service;

import goldstarproject.template.community.like.dto.HeartRequestDto;
import goldstarproject.template.security.auth.PrincipalDetails;

public interface HeartService {
    void boardInsertHeart(HeartRequestDto heartRequestDto, PrincipalDetails principalDetails) throws Exception;
    void boardDeleteHeart(HeartRequestDto heartRequestDto, PrincipalDetails principalDetails) throws Exception;
    void noticeInsertHeart(HeartRequestDto heartRequestDto, PrincipalDetails principalDetails) throws Exception;
    void noticeDeleteHeart(HeartRequestDto heartRequestDto, PrincipalDetails principalDetails) throws Exception;
    void questionInsertHeart(HeartRequestDto heartRequestDto, PrincipalDetails principalDetails) throws Exception;
    void questionDeleteHeart(HeartRequestDto heartRequestDto, PrincipalDetails principalDetails) throws Exception;
    void imageInsertHeart(HeartRequestDto heartRequestDto, PrincipalDetails principalDetails) throws Exception;
    void imageDeleteHeart(HeartRequestDto heartRequestDto, PrincipalDetails principalDetails) throws Exception;
    void imagesInsertHeart(HeartRequestDto heartRequestDto, PrincipalDetails principalDetails) throws Exception;
    void imagesDeleteHeart(HeartRequestDto heartRequestDto, PrincipalDetails principalDetails) throws Exception;



}
