package com.ssafy.tranvel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ResponseDto {
    private boolean result;

    private String msg;
}
