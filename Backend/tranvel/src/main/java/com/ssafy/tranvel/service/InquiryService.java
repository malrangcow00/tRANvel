package com.ssafy.tranvel.service;

import com.ssafy.tranvel.dto.InquiryDto;
import com.ssafy.tranvel.entity.Inquiry;
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
public class InquiryService {
    private final UserRepository userRepository;
    private final InquiryRepository inquiryRepository;


    public Inquiry createInquiry(InquiryDto inquiryDto) {
        Inquiry inquiry = Inquiry.builder()
                .title(inquiryDto.getTitle())
                .content(inquiryDto.getContent())
                .datetime(LocalDateTime.now().toString())
                .user(userRepository.findById(inquiryDto.getUserId()).get())
                .build();

        inquiryRepository.save(inquiry);
        return inquiry;
    }
}
