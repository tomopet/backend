package com.nap.tomopet.domain.auth.controller;

import com.nap.tomopet.domain.auth.dto.EmailRequestDto;
import com.nap.tomopet.domain.auth.dto.LoginRequestDto;
import com.nap.tomopet.domain.auth.dto.SignupRequestDto;
import com.nap.tomopet.domain.auth.service.EmailVerificationService;
import com.nap.tomopet.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final EmailVerificationService emailVerificationService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignupRequestDto requestDto) {
        userService.signUp(
                requestDto.getUsername(),
                requestDto.getPassword(),
                requestDto.getEmail(),
                requestDto.getNickname()
        );
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto requestDto) {
        String result = userService.login(requestDto.getUsername(), requestDto.getPassword());
        return ResponseEntity.ok(result);
    }
    @PostMapping("/email/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDto.Send request) {
        emailVerificationService.sendVerificationCode(request.getEmail());
        return ResponseEntity.ok("인증 코드가 이메일로 전송되었습니다.");
    }

    // 2. 인증번호 검증
    @PostMapping("/email/verify")
    public ResponseEntity<String> verifyEmail(@RequestBody EmailRequestDto.Verify request) {
        boolean isSuccess = emailVerificationService.confirmCode(request.getEmail(), request.getCode());
        if (isSuccess) {
            return ResponseEntity.ok("이메일 인증에 성공했습니다.");
        } else {
            return ResponseEntity.badRequest().body("인증번호가 일치하지 않거나 만료되었습니다.");
        }
    }
}
