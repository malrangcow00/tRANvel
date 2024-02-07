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

    @PostMapping("/image/room")
    public ResponseEntity<ResponseDto> saveImage(ImagePostDto imagePostDto) throws IOException {
        // image, roomId, contentId required

        /*
        MultipartFile image, String category, Long roomId, Long contentId
         */
        String image = imageUploadService.uploadImage(imagePostDto, "room");
        response = new ResponseDto(true, "Room 사진 s3 저장", image);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/image/food")
    public ResponseEntity<ResponseDto> saveFoodImage(ImagePostDto imagePostDto) throws IOException {
        // image, roomId, contentId required

        /*
        MultipartFile image, String category, Long roomId, Long contentId
         */

        // 사진 먼저 DB 에 저장하고, 해당 사진의 Raw Id 를 image 이름으로 사용

        String image = imageUploadService.uploadImage(imagePostDto, "food");
        response = new ResponseDto(true, "food 사진 s3 저장", image);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/image/attraction")
    public ResponseEntity<ResponseDto> saveAttractionImage(ImagePostDto imagePostDto) throws IOException {
        // image, roomId, contentId required

        String image = imageUploadService.uploadImage(imagePostDto, "attraction");
        response = new ResponseDto(true, "attraction 사진 s3 저장", image);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/image/adjustment")
    public ResponseEntity<ResponseDto> saveAdjustmentImage(ImagePostDto imagePostDto) throws IOException {
        // image, roomId, contentId required

        String image = imageUploadService.uploadImage(imagePostDto, "adjustment");
        response = new ResponseDto(true, "adjustment 사진 s3 저장", image);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
