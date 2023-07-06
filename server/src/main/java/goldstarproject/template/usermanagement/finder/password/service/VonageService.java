package goldstarproject.template.usermanagement.finder.password.service;


import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsClient;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import goldstarproject.template.member.entity.Member;
import goldstarproject.template.member.repository.MemberRepository;
import goldstarproject.template.usermanagement.finder.password.dto.VonageRequestDto;
import goldstarproject.template.usermanagement.finder.password.dto.VonageResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
@PropertySource("classpath:application-local.yaml")
public class VonageService {


    /**
     *  임시 비밀번호로 저장되기때문에 인증코드에 유효기간이 있으면 안됨
     */

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    private final String smsConfirmNum = createSmsKey();
    private final String apiKey;
    private final String secret;



    public VonageService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                         @Value("${spring.vonage.api.key}") String apiKey,
                         @Value("${spring.vonage.api.secret}") String secret) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.apiKey = apiKey;
        this.secret = secret;
    }



    public VonageResponseDto sendSMS(VonageRequestDto vonageRequestDto) {
        VonageClient vonageClient = new VonageClient.Builder()
                .apiKey(apiKey)
                .apiSecret(secret)
                .build();


        SmsClient smsClient = vonageClient.getSmsClient();

        String randomSecretKey = createSmsKey();

        String messageText = "[OneLine] " + "Message New Password : " + randomSecretKey;

        TextMessage message = new TextMessage(vonageRequestDto.getFrom(), vonageRequestDto.getTo(), messageText);
        SmsSubmissionResponse response = smsClient.submitMessage(message);
        VonageResponseDto vonageResponseDto = new VonageResponseDto();

        vonageResponseDto.setVerificationCode(randomSecretKey);
        vonageResponseDto.setNewPassword(vonageResponseDto.getVerificationCode());

        if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
            vonageResponseDto.setMessage("인증코드 발송 성공");
            Member member = memberRepository.findByPhoneAndNameAndUsername(vonageRequestDto.getPhone(), vonageRequestDto.getName(), vonageRequestDto.getUsername());

            if (member != null) {
                //String newPassword = vonageResponseDto.getVerificationCode(); // 새로운 패스워드 생성
                String rawPassword = vonageResponseDto.getVerificationCode();
                String newPassword = bCryptPasswordEncoder.encode(rawPassword);
                member.setPassword(newPassword);
                memberRepository.save(member);
            }
        } else {
            vonageResponseDto.setMessage("인증코드 발송 실패");
        }
        return vonageResponseDto;
    }


    /**
     * 발송 인증코드 생성
     * 8자의 문자열로 생성되며,유효성 검증에 따라 영문/숫자가 생성되는 문자열에 1자 이상은 포함되어야함
     */

    public static String createSmsKey() {
        String digits = "0123456789";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String characters = digits + lowercaseLetters;
        Random rnd = new Random();
        StringBuffer key = new StringBuffer();

        int digitIndex = rnd.nextInt(digits.length());
        char randomDigit = digits.charAt(digitIndex);
        key.append(randomDigit);

        for (int i = 0; i <= 6; i++) {
            int index = rnd.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            key.append(randomChar);
        }
        return key.toString();
    }
}
