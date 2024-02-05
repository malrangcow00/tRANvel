package com.ssafy.tranvel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JsonManagedReference
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "roomHistory", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    @Nullable
    private List<JoinUser> joinUser;

    public void joinUser(List<JoinUser> joinUser) {
        this.joinUser = joinUser;


    }

}
