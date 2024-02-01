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
public class AttractionList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 20, name = "Name")
    private String name;

    @Column(name = "Latitude")
    private double latitude;

    @Column(name = "Longitude")
    private double longitude;

    @Column(length = 100, name = "Description")
    private String description;

    @Column(length = 10, name = "Province")
    private String province;

    @Column(length = 10, name = "City")
    private String city;

    @Column(length = 20, name = "Image")
    private String image;
}
