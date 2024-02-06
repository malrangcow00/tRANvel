package com.ssafy.tranvel.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class ImagePostDto {

    private String email;

    @NotNull
    private MultipartFile image;

    private String category;

    private Long roomId;

    private Long contentId;

    /*
    MultipartFile image, String category, Long roomId, Long contentId
     */

}
