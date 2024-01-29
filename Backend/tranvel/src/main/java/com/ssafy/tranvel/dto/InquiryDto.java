package com.ssafy.tranvel.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InquiryDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

}
