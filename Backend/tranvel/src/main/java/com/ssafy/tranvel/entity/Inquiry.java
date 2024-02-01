package com.ssafy.tranvel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(length = 30, nullable = false, name = "Title")
    private String title;

    @Column(length = 1024, nullable = false, name = "Content")
    private String content;

    @Column(length = 30, nullable = false, name = "DateTime")
    private String datetime;

    @ManyToOne
    private User user;


}
