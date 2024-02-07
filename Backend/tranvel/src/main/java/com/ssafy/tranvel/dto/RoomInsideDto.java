package com.ssafy.tranvel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class RoomInsideDto<T> {

    private String roomCode;

    private String roomPassword;

//    private T joinUser;
    private T authority;
    // userid 를 받을 수 있다면, 접속한 유저의 authority 만 전달

}
