package com.bkb.metalmusicreviews.backend.bucket;

public enum BucketName {

    IMAGE(System.getenv("BUCKET_NAME"));

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
