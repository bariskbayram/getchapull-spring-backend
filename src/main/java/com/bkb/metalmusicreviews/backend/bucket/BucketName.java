package com.bkb.metalmusicreviews.backend.bucket;

public enum BucketName {

    IMAGE("metal-spring-web");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
