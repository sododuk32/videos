package goldstarproject.template.usermanagement.finder.username.controller;

import goldstarproject.template.usermanagement.finder.username.dto.UsernameDto;
import goldstarproject.template.usermanagement.finder.username.service.UsernameFindService;
import goldstarproject.template.usermanagement.finder.username.dto.UsernameFindDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.IllegalCharsetNameException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UsernameFindController {

    private final UsernameFindService nameFinderService;


    //아이디 찾기 예외 처리 필요함
    @PostMapping("/find")
    public ResponseEntity nameFinder(@RequestBody UsernameFindDto findMemberProfileDto) {
        if (findMemberProfileDto.getName() != null && findMemberProfileDto.getPhone() != null) {
            String response = nameFinderService.findUsername(findMemberProfileDto);
            if (response != null) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body("잘못된 이름 또는 전화번호입니다.");
            }
        } else {
            return ResponseEntity.badRequest().body("잘못된 이름 또는 전화번호입니다.");
        }
    }




    //아이디 중복 확인
    @GetMapping("/username")
    public ResponseEntity verifyUsername(@RequestParam ("username") String username) {
        try {
            boolean isDuplicate = nameFinderService.isUsernameDuplicate(username);
            if (isDuplicate) {
                return ResponseEntity.badRequest().body("중복된 유저네임입니다.");
            }
            return ResponseEntity.badRequest().body("사용 가능한 유저네임입니다.");
        } catch (IllegalCharsetNameException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
