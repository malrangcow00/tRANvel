package com.ssafy.tranvel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class RoomInsideDto<T> {

    private String roomCode;

    private String roomPassword;

    private T joinUser;

}
