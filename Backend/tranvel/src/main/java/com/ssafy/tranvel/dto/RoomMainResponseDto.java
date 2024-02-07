package com.ssafy.tranvel.dto;


import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.nio.channels.Pipe;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
@Builder
public class RoomMainResponseDto {
    /*
        List<RoomHistory>
        roomId, data, images, roomName, balanceResult
         */

    private Long roomid;

    private String roomName;

    private String startDate;

    private String endDate;

    private List<String> images;

    private int balanceResult;





}
