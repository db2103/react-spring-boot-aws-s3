package com.db2112.awsimageupload.bucket;

public enum BucketName {

    PROFILE_IMAGE("db2112");
    private  final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
