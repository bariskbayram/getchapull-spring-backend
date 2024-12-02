package com.bkb.getchapull.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class FileStoreService {

    private final AmazonS3 amazonS3;

    @Autowired
    public FileStoreService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void save(String path,
                     String filename,
                     Optional<Map<String, String>> optionalMetadata,
                     InputStream inputStream){

        ObjectMetadata metadata = new ObjectMetadata();
        optionalMetadata.ifPresent(map -> {
            if(!map.isEmpty()){
                map.forEach(metadata::addUserMetadata);
            }
        });

        try{
            amazonS3.putObject(path, filename, inputStream, metadata);
        }catch (AmazonServiceException e){
            throw new IllegalStateException("Failed to store file to s3", e);
        }
    }

    public byte[] download(String path, String key){
        try{
            S3Object object = amazonS3.getObject(path, key);
            S3ObjectInputStream inputStream = object.getObjectContent();
            return IOUtils.toByteArray(inputStream);
        }catch (AmazonServiceException | IOException e){
            throw new IllegalStateException("Failed to download", e);
        }
    }

    public void putProfilePhoto(String bucketname, String fileName, String copyFileName){
        amazonS3.copyObject(new CopyObjectRequest(bucketname, fileName , bucketname, copyFileName));
    }

    public void deleteImage(String path, String key){
        amazonS3.deleteObject(path, key);
    }

}
