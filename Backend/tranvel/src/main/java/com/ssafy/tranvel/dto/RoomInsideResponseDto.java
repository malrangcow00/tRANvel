package com.ssafy.tranvel.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomInsideResponseDto<T> {

    private Long roomId;

    private String roomCode;

    private String roomPassword;

//    private T joinUser;
    private T authority;
    // userid 를 받을 수 있다면, 접속한 유저의 authority 만 전달

}
