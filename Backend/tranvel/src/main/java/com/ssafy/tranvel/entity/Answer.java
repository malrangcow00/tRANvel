package com.ssafy.tranvel.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(length = 30, name = "Title")
    private String title;

    @Column(length = 1024, name = "Content")
    private String content;

    @Column(length = 30, name = "DateTime")
    private String dateTime;

    @ManyToOne
    @JsonBackReference
    private Inquiry inquiry;
}
