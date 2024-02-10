package com.ssafy.tranvel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodGameHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private RoomHistory roomHistory;

    @OneToMany(mappedBy = "foodGameHistory")
    @JsonManagedReference
    private List<FoodImage> images;

    @OneToOne
    @Nullable
    private RandomGame miniGameCode;

    @ElementCollection
    @Nullable
    private List<Long> selectedUsers = new ArrayList<>();

    @ElementCollection
    @Nullable
    private List<Long> unselectedUsers = new ArrayList<>();  // UserId X , JoinUserId O.
    // UserId,Nickname 등 뽑고 싶다면 AdjustmentGameHistoryService.getJoinUsers 참조

    @ElementCollection
    @Nullable
    private List<String> foodCandidates = new ArrayList<>();

    @Column(length = 20, name = "FoodName")
    @Nullable
    private String foodName;

    @Column(length = 30, name = "DateTime")
    private String dateTime;
}
