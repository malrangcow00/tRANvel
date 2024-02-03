package com.ssafy.tranvel.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
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
    public String uploadImage(MultipartFile image, String category, String imageName) throws IOException {
//        String fileName = image.getOriginalFilename();
        String fileName = imageName;
        InputStream inputStream = image.getInputStream();

        ObjectMetadata metadata =  new ObjectMetadata();
        metadata.setContentLength(image.getSize());

        String dir = bucketName + "/" + category;

        PutObjectRequest putObjectRequest = new PutObjectRequest(dir, fileName, inputStream, metadata);
        amazonS3.putObject(putObjectRequest);
        return category + "/" + fileName;
    }

    private ObjectMetadata getImage(MultipartFile file, String category) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        return objectMetadata;
    }

    /**
    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
    }
     **/


}
