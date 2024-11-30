package com.bkb.metalmusicreviews.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonConfig {

    @Bean
    public AmazonS3 amazonS3(){
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                System.getenv("AWS_ACCESSKEY_ID"),
                System.getenv("AWS_SECRET_ACCESSKEY")
        );

        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
