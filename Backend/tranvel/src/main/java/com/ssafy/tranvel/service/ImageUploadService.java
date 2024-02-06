package com.ssafy.tranvel.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.tranvel.dto.ImagePostDto;
import com.ssafy.tranvel.utility.SecurityUtility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@Getter @Setter
@RequiredArgsConstructor
public class ImageUploadService {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;
    public String uploadImage(ImagePostDto imagePostDto) throws IOException {
//        String fileName = image.getOriginalFilename();
        // MultipartFile image, String category, Long roomId, Long contentId
        String fileName;

        String dir;
        if (imagePostDto.getCategory().equals("profile")) {
            if (SecurityUtility.getCurrentUserId() != null) {
                fileName = SecurityUtility.getCurrentUserId();
            } else{
                fileName = imagePostDto.getEmail();
            }
            dir = "/" + imagePostDto.getCategory();
        } else {

            // 기록 별 해당 기록 id 로 저장
            fileName = imagePostDto.getContentId().toString();
            dir = "/" + imagePostDto.getRoomId() + "/" + imagePostDto.getCategory();
        }

        InputStream inputStream = imagePostDto.getImage().getInputStream();

        ObjectMetadata metadata =  new ObjectMetadata();
        metadata.setContentLength(imagePostDto.getImage().getSize());



        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName + dir, fileName, inputStream, metadata);
        amazonS3.putObject(putObjectRequest);
        return dir + "/" + fileName;
    }

//    private ObjectMetadata getImage(MultipartFile file, String category) {
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        objectMetadata.setContentType(file.getContentType());
//        objectMetadata.setContentLength(file.getSize());
//        return objectMetadata;
//    }

    /**
    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
    }
     **/


}
