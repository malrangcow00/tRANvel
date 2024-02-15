package com.ssafy.tranvel.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttractionList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name")
    @JsonProperty("관광지명")
    private String name;

    @Column(name = "Latitude")
    @JsonProperty("위도")
    private String latitude;

    @Column(name = "Longitude")
    @JsonProperty("경도")
    private String longitude;

    @Column(length = 5000, name = "Description")
    @JsonProperty("관광지소개")
    private String description;

//    @Column(length = 30, name = "Province")
//    @JsonProperty("제공기관명")
//    private String province;

    @Column(name = "City")
    @JsonProperty("제공기관명")
    private String city;

    @Column(name = "Image")
    @Nullable
    private String image;
}
