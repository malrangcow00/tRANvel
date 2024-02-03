package com.ssafy.tranvel.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseNullDto {

    private boolean result;

    private String msg;

}
