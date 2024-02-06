package com.ssafy.tranvel.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttractionListDto {

    private String name;

    private double latitude;

    private double longitude;

    private String description;

    private String province;

    private String city;

    private String image;

}
