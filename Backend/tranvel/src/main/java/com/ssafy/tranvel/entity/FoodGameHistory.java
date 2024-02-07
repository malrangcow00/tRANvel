package com.ssafy.tranvel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Long id;

    @JsonBackReference
    @ManyToOne
    private RoomHistory roomHistory;

    @JsonManagedReference
    @OneToMany(mappedBy = "foodGameHistory")
    private List<FoodImage> foodImages;

    @OneToOne
    private RandomGame miniGameCode;

    @Column(length = 10, name = "FoodName")
    private String foodName;

    @Column(length = 30, name = "DateTime")
    private String dateTime;
}
