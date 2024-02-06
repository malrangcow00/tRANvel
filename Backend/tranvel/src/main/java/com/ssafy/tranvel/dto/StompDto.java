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
        ENTER, CLOSE, NOTICE
    }

    private MessageType type;

    private String sender_id;

    private String roomId;

    private String message;
}