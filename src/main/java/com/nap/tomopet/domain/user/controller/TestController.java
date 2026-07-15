package com.nap.tomopet.domain.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/")
    public String home() {
        return "Tomopet 서버가 정상 작동 중입니다!";
    }
}
