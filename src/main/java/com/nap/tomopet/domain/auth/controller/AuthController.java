package com.nap.tomopet.domain.auth.controller;

import com.nap.tomopet.domain.user.dto.SignupRequestDto;
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
}
