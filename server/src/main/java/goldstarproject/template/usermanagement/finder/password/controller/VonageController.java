package goldstarproject.template.usermanagement.finder.password.controller;

import goldstarproject.template.usermanagement.finder.password.dto.VonageRequestDto;
import goldstarproject.template.usermanagement.finder.password.dto.VonageResponseDto;
import goldstarproject.template.usermanagement.finder.password.service.VonageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VonageController {

    /**
     * + 회원가입용 발송메서드 필요 [이번 주]
     */

    private final VonageService vonageService;


    @PostMapping("/send-sms-password")
    public ResponseEntity<VonageResponseDto> sendSms(@RequestBody VonageRequestDto vonageRequestDto ) throws Exception {
        VonageResponseDto response = vonageService.sendSMS(vonageRequestDto);
        return ResponseEntity.ok(response);
    }
}
