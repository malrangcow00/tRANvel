package com.ssafy.tranvel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long userId;

    @Column(length = 30, nullable = false, name = "Title")
    private String title;

    @Column(length = 1024, nullable = false, name = "Content")
    private String content;

    @Column(length = 30, nullable = false, name = "DateTime")
    private String datetime;

    @ManyToOne
    @JsonBackReference
    private User user;


    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.datetime = LocalDateTime.now().toString();

    }

}
