package com.ssafy.tranvel.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AttractionBaseDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("관광지명")
    private String attractionName;

    @JsonProperty("관광지구분")
    private String attractionType;

    @JsonProperty("소재지도로명주소")
    private String roadAddress;

    @JsonProperty("소재지지번주소")
    private String parcelAddress;

    @JsonProperty("위도")
    private String latitude;

    @JsonProperty("경도")
    private String longitude;

    @JsonProperty("면적")
    private String area;

    @JsonProperty("공공편익시설정보")
    private String publicFacilityInfo;

    @JsonProperty("숙박시설정보")
    private String accommodationInfo;

    @JsonProperty("운동및오락시설정보")
    private String sportsAndEntertainmentInfo;

    @JsonProperty("휴양및문화시설정보")
    private String leisureAndCultureInfo;

    @JsonProperty("접객시설정보")
    private String receptionFacilityInfo;

    @JsonProperty("지원시설정보")
    private String supportFacilityInfo;

    @JsonProperty("지정일자")
    private String designatedDate;

    @JsonProperty("수용인원수")
    private String capacity;

    @JsonProperty("주차가능수")
    private String parkingAvailability;

    @JsonProperty("관광지소개")
    @Column(length = 5000)
    private String introduction;

    @JsonProperty("관리기관전화번호")
    private String managementPhone;

    @JsonProperty("관리기관명")
    private String managementAgency;

    @JsonProperty("데이터기준일자")
    private String dataStandardDate;

    @JsonProperty("제공기관코드")
    private String providingAgencyCode;

    @JsonProperty("제공기관명")
    private String providingAgencyName;
}
