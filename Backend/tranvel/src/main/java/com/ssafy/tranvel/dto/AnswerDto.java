package com.ssafy.tranvel.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AnswerDto {

    private int inquiryId;

    private int answerId;

    private String title;

    private String content;

}
