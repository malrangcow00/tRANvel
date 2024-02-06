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
public class RandomGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int miniGameCode;

    @Column(length = 16, name = "GameName")
    private String gameName;
}
