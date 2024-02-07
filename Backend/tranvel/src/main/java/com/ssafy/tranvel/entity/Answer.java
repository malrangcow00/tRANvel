package com.ssafy.tranvel.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(length = 30, name = "Title")
    private String title;

    @Column(length = 1024, name = "Content")
    private String content;

    @Column(length = 30, name = "DateTime")
    private String dateTime;

    @ManyToOne
    @JsonBackReference
    private Inquiry inquiry;


    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.dateTime = LocalDateTime.now().toString();

    }
}
