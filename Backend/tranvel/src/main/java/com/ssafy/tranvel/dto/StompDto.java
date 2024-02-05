package com.ssafy.tranvel.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StompDto {

    public enum MessageType {
        ENTER, ALARM, NOTICE
    }

    private MessageType type;

    private Long sender_id;

    private Long roomId;

    private String message;
}