package com.ssafy.tranvel.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdjustmentGameHistoryDto {

    private Long id;

    private Long roomId;

    private Long miniGameCodeId;

    private String targetUser;

    private String dateTime;

    private int price;

    private List<Long> selectedUsers; // 일단 id로 받음..

    private List<GamePlayerDto> moneyResults; // 유저들의 정산 결과들

    private String image;

    private String category;

    private String detail;
}
