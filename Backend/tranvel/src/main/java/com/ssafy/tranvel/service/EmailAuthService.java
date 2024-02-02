package com.ssafy.tranvel.service;

import com.ssafy.tranvel.dto.EmailDto;
import com.ssafy.tranvel.repository.EmailAuthDao;

import com.ssafy.tranvel.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class EmailAuthService {

    @Value("${spring.mail.username}")
    private String setFrom;
    private String verificationCode;
    public String accessEmail;

    private final EmailAuthDao emailAuthDao;
    private final JavaMailSender emailSender;
    private final UserRepository userRepository;

    public boolean emailDuplication(EmailDto emailDto) {
        return userRepository.findByEmail(emailDto.getEmail()).isPresent();
    }

    public String createVerificationCode() {
        Random random = new Random();
        StringBuffer tmpCode = new StringBuffer();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Set<Character> usedChars = new HashSet<>();
        // 4자리의 랜덤 코드 생성
        while (tmpCode.length() < 4) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            // 중복 검사
            if (!usedChars.contains(randomChar)) {
                tmpCode.append(randomChar);
                usedChars.add(randomChar);
            }
        }
        return tmpCode.toString();
    }

    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {
        String createdCode =createVerificationCode();
        String toEmail = email;
        String title = "[Tranvel] 회원가입 인증을 완료해주세요.";

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail);
        message.setSubject(title);

        String msgOfEmail = "안녕하세요, Tranvel 입니다."
                + "<br>"
                + "이메일 인증을 완료하시려면 아래의 인증 코드를 입력해주세요."
                + "<br>"
                + "인증 번호 : <strong>"
                + createdCode
                + "</strong>"
                + "<br>";

        message.setFrom(setFrom);
        message.setText(msgOfEmail, "utf-8", "html");
        createCodeInRedis(email, createdCode);
        emailSender.send(message);
        return message;
    }

    public void createCodeInRedis(String email, String verificationCode) {
        emailAuthDao.createEmailAuthentication(email, verificationCode);
    }

    public boolean verifyEmail(String email, String verificationCode) {
        if (isVerify(email, verificationCode)) {
            return false;
        }
        // 자격 부여 메서드 실행
        emailAuthDao.giveAuthority(email);
        accessEmail = email;
        // 인증 코드 유효성 통과 시, 해당 인증 코드가 사라지기 때문에 재접근 시 새로운 코드 발급 필요
        emailAuthDao.removeEmailAuthentication(email);
        return true;
    }

    private boolean isVerify(String email, String verificationCode) {
        return !(emailAuthDao.hasKey(email) &&
                emailAuthDao.getEmailAuthentication(email)
                        .equals(verificationCode));
    }
}
