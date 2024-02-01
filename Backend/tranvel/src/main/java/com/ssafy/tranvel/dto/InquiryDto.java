package com.ssafy.tranvel.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InquiryDto {
    @NotNull
    private int userId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

}
