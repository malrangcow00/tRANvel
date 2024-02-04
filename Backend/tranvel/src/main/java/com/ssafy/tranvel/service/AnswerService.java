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
import java.time.ZoneId;
import java.util.List;

@Service
@Getter @Setter
@RequiredArgsConstructor
public class AnswerService {

    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;

    public Answer createAnswer(AnswerDto answerDto) {
        Inquiry inquiry = inquiryRepository.findById(answerDto.getInquiryId()).get();

        Answer answer = Answer.builder()
                .title(answerDto.getTitle())
                .content(answerDto.getContent())
                .dateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")).toString())
                .inquiry(inquiry)
                .build();

        answerRepository.save(answer);
        
        return answer;
    }



    public Answer getAnswer(AnswerDto answerDto) {
        Answer answer = answerRepository.findById(answerDto.getAnswerId()).get();
        return answer;
    }


    public Answer updateAnswer(AnswerDto answerDto) {
        Answer answer = answerRepository.findById(answerDto.getInquiryId()).get();
        answer.update(answerDto.getTitle(), answerDto.getContent());
        answerRepository.save(answer);
        return answer;
    }


    public void deleteAnswer(AnswerDto answerDto) {
        answerRepository.deleteById(answerDto.getAnswerId());
    }
}
