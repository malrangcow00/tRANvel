package com.ssafy.tranvel.dto;


import com.ssafy.tranvel.utility.SecurityUtility;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AnswerDto {

    private Long inquiryId;

    private Long answerId;

    private String title;

    private String content;

}


