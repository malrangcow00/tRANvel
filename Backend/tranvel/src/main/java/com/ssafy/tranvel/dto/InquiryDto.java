package com.ssafy.tranvel.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InquiryDto {
//    @NotNull
    private String userEmail;

//    private Long userId;

    private Long inquiryId;

    private String title;

    private String content;

}
