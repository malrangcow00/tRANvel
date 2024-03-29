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

    public boolean emailDuplication(String email) {
        return userRepository.findByEmail(email).isPresent();
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
        String createdCode = createVerificationCode();
        String toEmail = email;
        String title = "[tRANvel] 회원가입 인증";

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail);
        message.setSubject(title);

        String msgOfEmail =
                "<div class=\"container\" style=\"text-align: center; width: 100%; position: relative;\">"
//                + "<img class=\"overlay-img responsive-img\" style=\"width: 40%;\" src=\"https://lh3.google.com/u/0/d/15K0WC6R8L23zDxpFVkzbZSTBK6cjCRSn=w1920-h919-iv1\" alt=\"tRANvel Logo\"/>"
                + "<p class=\"overlay-text\" style=\"top: 50%; left: 50%; color: #5caad2; font-size: 70px; margin: 0; z-index: 1;\"><strong>tRANvel</strong></p>"
                + "<p style=\"margin: 10px 0; font-size: 20px;\"><strong>랜덤 여행지 추천 서비스</strong></p>"
                + "<p> </p>"
                + "<p style=\"font-size: 15px;\"><strong>안녕하세요, tRANvel 여행자님!</strong></p>"
                + "<p style=\"font-size: 15px;\"><strong>아래의 인증 코드를 입력하여 회원가입을 완료해주세요.</strong></p>"
                + "<div style=\"background-color: #5caad2; color: #ffffff; padding: 20px; width: 45%; height: 50%; font-size: 50px; font-family: 'Work Sans', Calibri, sans-serif; border-radius: 20px; margin: 0 auto;\">"
                + "<strong>"
                + createdCode
                + "</strong>"
                + "</div>"
                + "<p style=\"font-size: 15px;\"><strong>tRANvel과 함께 더 멋진 여행을 떠나보세요!</strong></p>"
                + "<p style=\"font-size: 15px;\"><strong>감사합니다!</strong></p>"
                + "<br>"
                + "<p style=\"text-align: right; margin-right: 40px;\"><strong>tRANvel 팀</strong></p>"
                + "</div>";

        message.setFrom(setFrom);
        message.setContent(msgOfEmail, "text/html; charset=utf-8");
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
