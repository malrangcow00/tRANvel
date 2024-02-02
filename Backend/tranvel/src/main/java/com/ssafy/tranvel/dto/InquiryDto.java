package com.ssafy.tranvel.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InquiryDto {
    @NotNull
    private int userId;

    private int inquiryId;

    private String title;

    private String content;

}
