package com.ssafy.tranvel.dto;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AdjustmentResponseDto {

    private Long id;

    private String dateTime;

    private int price;

    private int moneyResult;

    private List<Long> selectedUsers;

    private List<String > images;

    private String category;

    private String detail;

    private String location;
}
