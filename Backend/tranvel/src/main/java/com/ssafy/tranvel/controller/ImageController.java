package com.ssafy.tranvel.controller;

import com.ssafy.tranvel.dto.ImagePostDto;
import com.ssafy.tranvel.dto.ResponseDto;
import com.ssafy.tranvel.service.ImageUploadService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Getter @Setter
@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageUploadService imageUploadService;
    private ResponseDto response;

    @PostMapping("/image")
    public ResponseEntity<ResponseDto> saveImage(ImagePostDto imagePostDto) throws IOException {

        /*
        MultipartFile image, String category, Long roomId, Long contentId
         */
        String profileimage = imageUploadService.uploadImage(imagePostDto);
        response = new ResponseDto(true, "프로필 사진 s3 저장", profileimage);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
