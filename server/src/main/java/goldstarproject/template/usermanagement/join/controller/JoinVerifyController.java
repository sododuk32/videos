package goldstarproject.template.usermanagement.join.controller;


import goldstarproject.template.usermanagement.join.dto.VerifyRequestDto;
import goldstarproject.template.usermanagement.join.dto.VerifyResponseDto;
import goldstarproject.template.usermanagement.join.service.VerifyCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class JoinVerifyController {

    private final VerifyCodeService verifyCodeService;



    @PostMapping("send-join")
    public ResponseEntity verifyCode(@RequestBody VerifyRequestDto verifyRequestDto ) {
        VerifyResponseDto response = verifyCodeService.sendVerifyCode(verifyRequestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateVerificationCode(@RequestParam("code") String verificationCode) {
        if (verificationCode.isEmpty()) {
            return ResponseEntity.badRequest().body("Verification code is empty.");
        }

        boolean isValid = verifyCodeService.isVerificationCodeValid(verificationCode);
        if (!isValid) {
            return ResponseEntity.badRequest().body("Invalid verification code.");
        }
        return ResponseEntity.ok(isValid);
    }
}
