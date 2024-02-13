package com.ssafy.tranvel.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.tranvel.entity.AttractionList;
import com.ssafy.tranvel.repository.AttractionListRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Getter @Setter
@RestController
@RequiredArgsConstructor
public class TmpController {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    private final AttractionListRepository attractionListRepository;

    @PostMapping(value = "/tmp",  consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public String inputImage(String attractionName, MultipartFile image) throws IOException {
        AttractionList attractionList = attractionListRepository.findByName(attractionName).get();


        String fileName = attractionList.getName() + ".jpg";

        String dir = "/attractionList";

        InputStream inputStream = image.getInputStream();

        ObjectMetadata metadata =  new ObjectMetadata();

        metadata.setContentLength(image.getSize());

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName + dir, fileName, inputStream, metadata);
        amazonS3.putObject(putObjectRequest);

        attractionList.setImage(dir + "/" + fileName);
        attractionListRepository.save(attractionList);
        return dir + "/" + fileName;

    }
}