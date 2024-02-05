package com.ssafy.tranvel.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GamePlayerDto {

    private Long userId;

    private int moneyResult; // 해당 유저의 증감
}
