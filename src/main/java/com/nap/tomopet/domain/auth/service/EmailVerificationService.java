package com.nap.tomopet.domain.auth.service;

import com.nap.tomopet.global.infra.mail.EmailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailSendService emailSendService;
    private final Map<String, VerificationInfo> verificationStorage = new ConcurrentHashMap<>();
    private static final long VERIFICATION_EXPIRATION_MINUTES = 3;

    public void sendVerificationCode(String email) {
        Random random = new Random();
        String code = String.format("%06d", random.nextInt(1000000));
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(VERIFICATION_EXPIRATION_MINUTES);

        verificationStorage.put(email, new VerificationInfo(code, expiresAt, false));

        String subject = "[TomoPet] 회원가입 이메일 인증 안내";
        String text = "안녕하세요. TomoPet입니다.\n\n" +
                "회원가입을 위한 인증번호는 [" + code + "] 입니다.\n" +
                "3분 이내에 입력해 주세요.";

        emailSendService.sendEmail(email, subject, text);
    }

    public boolean confirmCode(String email, String code) {
        VerificationInfo info = verificationStorage.get(email);
        if (info == null) return false;
        if (info.isExpired()) {
            verificationStorage.remove(email);
            return false;
        }
        if (!info.getCode().equals(code)) return false;

        info.setVerified(true);
        return true;
    }

    public boolean isEmailVerified(String email) {
        VerificationInfo info = verificationStorage.get(email);
        return info != null && info.isVerified() && !info.isExpired();
    }

    public void clearVerification(String email) {
        verificationStorage.remove(email);
    }

    private static class VerificationInfo {
        private final String code;
        private final LocalDateTime expiresAt;
        private boolean isVerified;

        public VerificationInfo(String code, LocalDateTime expiresAt, boolean isVerified) {
            this.code = code;
            this.expiresAt = expiresAt;
            this.isVerified = isVerified;
        }

        public String getCode() { return code; }
        public boolean isExpired() { return LocalDateTime.now().isAfter(expiresAt); }
        public boolean isVerified() { return isVerified; }
        public void setVerified(boolean verified) { isVerified = verified; }
    }
}