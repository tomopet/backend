package com.nap.tomopet.domain.user.service;

import com.nap.tomopet.domain.user.entity.User;
import com.nap.tomopet.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    //회원가입 서비스 로직
    @Transactional
    public Long signUp(String username, String password, String email,  String nickname) {

        //아이디 중복 검사
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        //비밀번호 암호화
        String encodePassword = passwordEncoder.encode(password);

        //유저 엔티티 create, save
        User user = User.builder()
                .username(username)
                .email(email)
                .password(encodePassword)
                .nickname(nickname)
                .build();

        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public String login(String username, String password) {

        //username을 사용해 유저 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디 입니다."));


        //암호화된 비밀번호와 입력된 비밀번호가 일치하는지 비교
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return "로그인 성공";
    }




}
