package com.ssafy.tranvel.dto;


import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RoomHistoryDto {

    @Nullable
    private Long userId;

    private String userEmail;

    private String roomCode;

    private String roomName;

    private String roomPassword;

    private String startDate;

    private String endDate;

    private int balanceResult;

    private Long roomId;

}
