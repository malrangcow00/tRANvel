package com.ssafy.tranvel.config;
//
//
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
//import software.amazon.awssdk.auth.credentials.AwsCredentials;
//import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.presigner.S3Presigner;
//
//@Configuration
//public class S3Config {
//
//    @Value("${cloud.aws.region}")
//    private String region;
//
//    @Value("${cloud.aws.credentials.access-key}")
//    private String accessKey;
//
//    @Value("${cloud.aws.credentials.secret-key}")
//    private String secretKey;
//
//    @Value("${cloud.aws.s3.bucketName}")
//    private String bucketName;
//
//    @Bean
//    public AmazonS3 s3Builder() {
//        AWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
//
//        return AmazonS3ClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
//                .withRegion(region).build();
//    }
////    @Bean
////    public AwsCredentials basicAWSCredentials() {
////        return AwsBasicCredentials.create(accessKey, secretKey);
////    }
////
////    @Bean
////    public S3Presigner s3Presigner(AwsCredentials awsCredentials) {
////        return S3Presigner.builder()
////                .region(Region.AP_NORTHEAST_2)
////                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
////                .build();
////    }
////
////    @Bean
////    public S3Client s3Client (AwsCredentials awsCredentials) {
////        return S3Client.builder()
////                .region(Region.AP_NORTHEAST_2)
////                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
////                .build();
////    }
//}
