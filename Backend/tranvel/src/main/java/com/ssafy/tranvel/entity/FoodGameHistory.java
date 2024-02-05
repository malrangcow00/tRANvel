package com.ssafy.tranvel.entity;

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
public class FoodGameHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Long;

    @ManyToOne
    private RoomHistory roomHistory;

    @OneToMany(mappedBy = "foodGameHistory")
    private List<FoodImage> foodImages;

    @OneToOne
    private RandomGame miniGameCode;

    @Column(length = 10, name = "FoodName")
    private String foodName;

    @Column(length = 30, name = "DateTime")
    private String dateTime;
}
