package com.ssafy.tranvel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 6, name = "RoomCode")
    private String roomCode;

    @Nullable
    @Column(length = 16, name = "RoomName")
    private String roomName;

    @Column(length = 16, name = "RoomPW")
    private String roomPassword;

    @Column(length = 30, name = "StartDate")
    private String startDate;

    @Nullable
    @Column(length = 30, name = "EndDate")
    private String endDate;

    @Column(name = "BalanceResult")
    private int balanceResult;

    @Column(name = "NowPlaying")
    private boolean nowPlaying;

    @OneToMany(mappedBy = "roomHistory")
    @JsonManagedReference
    private List<AdjustmentGameHistory> adjustmentGameHistories;

    @OneToMany(mappedBy = "roomHistory")
    @JsonManagedReference
    private List<AttractionGameHistory> attractionGameHistories;

    @OneToMany(mappedBy = "roomHistory")
    @JsonManagedReference
    private List<FoodGameHistory> foodGameHistories;


    @OneToMany(mappedBy = "roomHistory")
    @JsonManagedReference
    private List<RoomImage> images;

    @ManyToOne
    @JsonBackReference
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "roomHistory", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    @Nullable
    private List<JoinUser> joinUser;

    @JsonManagedReference
    @OneToMany(mappedBy = "roomHistory")
    private List<AdjustmentGameHistory> adjustmentGameHistories;

    @JsonManagedReference
    @OneToMany(mappedBy = "roomHistory")
    private List<AttractionGameHistory> attractionGameHistories;

    @JsonManagedReference
    @OneToMany(mappedBy = "roomHistory")
    private List<FoodGameHistory> foodGameHistories;

    public void joinUser(List<JoinUser> joinUser) {
        this.joinUser = joinUser;
    }

    public void finishRoom() {
        this.nowPlaying = false;
        this.roomCode = "";
        this.endDate = LocalDateTime.now(ZoneId.of("Asia/Seoul")).toString();
    }

}
