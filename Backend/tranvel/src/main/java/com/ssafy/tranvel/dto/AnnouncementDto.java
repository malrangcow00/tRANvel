package com.ssafy.tranvel.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AnnouncementDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

}
