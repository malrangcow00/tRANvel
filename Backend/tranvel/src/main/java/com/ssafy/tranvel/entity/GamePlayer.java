package com.ssafy.tranvel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "joinUser_id")
    @JsonBackReference
    private JoinUser joinUser;

//    @ManyToOne
//    @JoinColumn(name = "adjustmentGameHistory_id")
//    private AdjustmentGameHistory adjustmentGameHistory;

    @Column(name = "MoneyResult")
    private int moneyResult;
}
