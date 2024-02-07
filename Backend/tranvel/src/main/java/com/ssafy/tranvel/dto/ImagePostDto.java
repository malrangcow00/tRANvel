package com.ssafy.tranvel.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class ImagePostDto {

    private Long roomId;

    private Long contentId;


}
