package com.ssafy.tranvel.service;


import com.ssafy.tranvel.dto.AnswerDto;
import com.ssafy.tranvel.entity.Answer;
import com.ssafy.tranvel.entity.Inquiry;
import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.AnswerRepository;
import com.ssafy.tranvel.repository.InquiryRepository;
import com.ssafy.tranvel.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Getter @Setter
@RequiredArgsConstructor
public class AnswerService {

    private InquiryRepository inquiryRepository;
    private UserRepository userRepository;
    private AnswerRepository answerRepository;

    public Answer createAnswer(AnswerDto answerDto) {
        Inquiry inquiry = inquiryRepository.findById(answerDto.getInquiryId()).get();
        String user = inquiry.getUser().getEmail();
        // 토큰에 담긴 email 과 일치하지 않으면 error
//        if (!user.equals(answerDto.getToken())) {
//
//        }
        Answer answer = Answer.builder()
                .title(answerDto.getTitle())
                .content(answerDto.getContent())
                .dateTime(LocalDateTime.now().toString())
                .inquiry(inquiry)
                .build();

        answerRepository.save(answer);
        
        return answer;
    }
}
