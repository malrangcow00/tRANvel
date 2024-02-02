package com.ssafy.tranvel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 16, name = "RoomCode")
    private String roomCode;

    @Column(length = 16, name = "RoomName")
    private String roomName;

    @Column(length = 16, name = "RoomPW")
    private String roomPW;

    @Column(length = 30, name = "StartDate")
    private String startDate;

    @Column(length = 30, name = "EndDate")
    private String endDate;

    @Column(name = "BalanceResult")
    private int blanceResult;

    @Column(name = "NowPlaying")
    private boolean nowPlaying;
}
