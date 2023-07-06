package goldstarproject.template.usermanagement.join.service;


import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsClient;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import goldstarproject.template.usermanagement.finder.password.VO.VerificationCode;
import goldstarproject.template.usermanagement.finder.password.service.VonageService;
import goldstarproject.template.usermanagement.join.dto.VerifyRequestDto;
import goldstarproject.template.usermanagement.join.dto.VerifyResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@PropertySource("classpath:application-local.yaml")
public class VerifyCodeService {

    /**
     * + 회원가입 시 인증코드 발송
     *    - 생성되어 전송된 코드와 사용자가 입력한 코드가 일치하는지 확인
     */

    private final VonageService vonageService;
    private final VonageClient vonageClient;

    private final String apiKey;
    private final String secret;



    private final Map<String, VerificationCode> verifyCodes = new HashMap<>();



    public VerifyCodeService(VonageService vonageService, VonageClient vonageClient,
                             VonageClient vonageClient1, @Value("${spring.vonage.api.key}") String apiKey,
                             @Value("${spring.vonage.api.secret}") String secret) {
        this.vonageService = vonageService;
        this.vonageClient = vonageClient1;
        this.apiKey = apiKey;
        this.secret = secret;
    }


    public VerifyResponseDto sendVerifyCode(VerifyRequestDto verifyRequestDto) {

        SmsClient smsClient = vonageClient.getSmsClient();

        //랜덤 코드 생성
        String randomCode = vonageService.createSmsKey();

        //수신되는 메세지
        String messageText = "[OneLine] " + "Welcome OneLine VerifyCode :" + randomCode;

        //인증코드 유효기간 10분
        long validDuration = 10 * 60 * 1000;  //10분
        long expirationTime = System.currentTimeMillis() + validDuration;
        verifyCodes.put(randomCode, new VerificationCode(randomCode,expirationTime));

        TextMessage responseMessage = new TextMessage(verifyRequestDto.getFrom(), verifyRequestDto.getTo(),messageText) ;
        SmsSubmissionResponse response = smsClient.submitMessage(responseMessage);

        VerifyResponseDto verifyResponseDto = new VerifyResponseDto();
        verifyResponseDto.setJoinVerifyCode(randomCode);


        if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
            verifyResponseDto.setMessage("회원가입 인증코드 발송 성공");
        } else {
            verifyResponseDto.setMessage("발송 실패");
        }
        return verifyResponseDto;

    }
    public boolean isVerificationCodeValid(String verificationCode) {
        VerificationCode code = verifyCodes.get(verificationCode);
        if (code != null) {
            return System.currentTimeMillis() <= code.getExpirationTime();
        }
        return false;
    }
}
