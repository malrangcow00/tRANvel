package com.ssafy.tranvel.controller;

import com.ssafy.tranvel.dto.ImagePostDto;
import com.ssafy.tranvel.dto.ResponseDto;
import com.ssafy.tranvel.service.ImageUploadService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Getter @Setter
@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final ImageUploadService imageUploadService;
    private ResponseDto response;

    @PostMapping(value = "/room", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})

    // Param 두개 나눠서 앞은 Dto, 뒤는 Multipart
    public ResponseEntity<ResponseDto> saveImage(@RequestPart ImagePostDto imagePostDto, @RequestPart(value = "image",required = true) MultipartFile image) throws IOException {

        imageUploadService.uploadImage(imagePostDto, image, "room");
        response = new ResponseDto(true, "Room 사진 s3 저장", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping(value = "/food", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> saveFoodImage(@RequestPart ImagePostDto imagePostDto, @RequestPart(value = "image",required = true) MultipartFile image) throws IOException {

        imageUploadService.uploadImage(imagePostDto, image, "food");
        response = new ResponseDto(true, "food 사진 s3 저장", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping(value = "/attraction", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> saveAttractionImage(@RequestPart ImagePostDto imagePostDto, @RequestPart(value = "image",required = true) MultipartFile image) throws IOException {

        imageUploadService.uploadImage(imagePostDto, image, "attraction");
        response = new ResponseDto(true, "attraction 사진 s3 저장", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping(value = "/adjustment", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> saveAdjustmentImage(@RequestPart ImagePostDto imagePostDto, @RequestPart(value = "image",required = true) MultipartFile image) throws IOException {
        // image, roomId, contentId required

        imageUploadService.uploadImage(imagePostDto, image, "adjustment");
        response = new ResponseDto(true, "adjustment 사진 s3 저장", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
