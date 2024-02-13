package com.ssafy.tranvel.dto;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RoomDetailResponseDto {

    private Long contentId;

    private String historyCategory;

    private String dateTime;

    private List<String> images;

    private String detail; // city, food

    private int moneyResult;


}
