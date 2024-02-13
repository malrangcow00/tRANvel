package com.ssafy.tranvel.entity;


import com.ssafy.tranvel.repository.AnnouncementRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, name = "Title")
    private String title;

    @Column(length = 1024, name = "Content")
    private String content;

    @Column(length = 30, name = "DateTime")
    private String dateTime;


    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.dateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")).toString();

    }
}
