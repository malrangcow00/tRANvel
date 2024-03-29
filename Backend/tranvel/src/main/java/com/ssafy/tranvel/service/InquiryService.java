package com.ssafy.tranvel.service;

import com.ssafy.tranvel.dto.InquiryDto;
import com.ssafy.tranvel.dto.UserDto;
import com.ssafy.tranvel.entity.Inquiry;
import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.InquiryRepository;
import com.ssafy.tranvel.repository.UserRepository;
import com.ssafy.tranvel.utility.SecurityUtility;
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
public class InquiryService {

    private final UserRepository userRepository;
    private final InquiryRepository inquiryRepository;

    public Inquiry createInquiry(String title, String content) {
        Inquiry inquiry = Inquiry.builder()
                .title(title)
                .content(content)
                .datetime(LocalDateTime.now(ZoneId.of("Asia/Seoul")).toString())
                .user(userRepository.findByEmail(SecurityUtility.getCurrentUserId()).get())
                .build();

        inquiryRepository.save(inquiry);
        return inquiry;
    }

    public List<Inquiry> getAllInquiries() {
        User user = userRepository.findByEmail(SecurityUtility.getCurrentUserId()).get();
        return user.getInquiryList();
    }


    public Inquiry getInquiry(Long inquiryId) {
        User user = userRepository.findByEmail(SecurityUtility.getCurrentUserId()).get();
        List<Inquiry> inquiries = user.getInquiryList();
        Inquiry inquiry = inquiries.stream().filter(inq -> inq.getId() == inquiryId)
                .findFirst().orElseThrow (() -> new IllegalArgumentException("해당번호의 문의 글이 존재하지 않습니다."));
        return inquiry;
    }


    public Inquiry updateInquiry(InquiryDto inquiryDto) {
        User user = userRepository.findByEmail(SecurityUtility.getCurrentUserId()).get();
        List<Inquiry> inquiries = user.getInquiryList();
        Inquiry inquiry = inquiries.stream().filter(inq -> inq.getId() == inquiryDto.getInquiryId())
                .findFirst().orElseThrow(() -> new IllegalArgumentException("해당번호의 문의 글이 존재하지 않습니다."));

        inquiry.update(inquiryDto.getTitle(), inquiryDto.getContent());
        inquiryRepository.save(inquiry);

        return inquiry;
    }

    public void deleteInquiry(Long inquiryId) {
        User user = userRepository.findByEmail(SecurityUtility.getCurrentUserId()).get();
        List<Inquiry> inquiries = user.getInquiryList();
        Inquiry inquiry = inquiries.stream().filter(inq -> inq.getId() == inquiryId)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("해당번호의 문의 글이 존재하지 않습니다."));
        inquiryRepository.delete(inquiry);

    }

}
