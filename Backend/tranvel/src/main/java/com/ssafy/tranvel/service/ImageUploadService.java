package com.ssafy.tranvel.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.tranvel.dto.ImagePostDto;
import com.ssafy.tranvel.entity.*;
import com.ssafy.tranvel.repository.*;
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

    private final RoomHistoryRepository roomHistoryRepository;
    private final FoodGameHistoryRepository foodGameHistoryRepository;
    private final AdjustmentGameHistoryRepository adjustmentGameHistoryRepository;
    private final AttractionGameRepository attractionGameRepository;
    private final RoomImageRepository roomImageRepository;
    private final FoodImageRepository foodImageRepository;
    private final AdjustmentImageRepository adjustmentImageRepository;
    private  final AttractionImageRepository attractionImageRepository;


    public String uploadImage(ImagePostDto imagePostDto, String category) throws IOException {
//        String fileName = image.getOriginalFilename();
        // MultipartFile image, String category, Long roomId, Long contentId
        String fileName;

        String dir;
        if (category.equals("profile")) {
            if (SecurityUtility.getCurrentUserId() != null) {
                fileName = SecurityUtility.getCurrentUserId();
            } else{
                fileName = imagePostDto.getEmail();
            }
            dir = "/" + category;
        } else {
            switch (category) {
                case "food" :
                    FoodImage foodImage = FoodImage.builder()
                            .foodGameHistory(foodGameHistoryRepository.findById(imagePostDto.getContentId()).get())
                            .build();
                    ;
                    fileName = foodImageRepository.save(foodImage).getId().toString();
                    dir = "/" + imagePostDto.getRoomId() + "/" + category;
                    break;
                case "adjustment" :
                    AdjustmentImage adjustmentImage = AdjustmentImage.builder()
                            .adjustmentGameHistory(adjustmentGameHistoryRepository.findById(imagePostDto.getContentId()).get())
                            .build();

                    fileName = adjustmentImageRepository.save(adjustmentImage).getId().toString();
                    dir = "/" + imagePostDto.getRoomId() + "/" + category;
                    break;
                case "attraction":
                    AttractionImage attractionImage = AttractionImage.builder()
                            .attractionGameHistory(attractionGameRepository.findById(imagePostDto.getContentId()).get())
                            .build();

                    fileName = attractionImageRepository.save(attractionImage).getId().toString();
                    dir = "/" + imagePostDto.getRoomId() + "/" + category;
                    break;
                default:
                    RoomImage roomImage = RoomImage.builder()
                            .roomHistory(roomHistoryRepository.findById(imagePostDto.getRoomId()).get())
                            .build();

                    fileName = roomImageRepository.save(roomImage).getId().toString();
                    dir = "/" + imagePostDto.getRoomId();
            }
            // 기록 별 해당 기록 id 로 저장
//            fileName = imagePostDto.getContentId().toString();

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
