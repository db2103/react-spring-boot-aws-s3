package com.db2112.awsimageupload.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class FileStore {

    private final AmazonS3 s3;


    @Autowired
    public FileStore(AmazonS3 s3) {
        this.s3 = s3;
    }

    public void save(String path, String fileName, Optional<Map<String, String>> optionalMetaData, InputStream inputStream) {

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            optionalMetaData.ifPresent(map -> {
                if (!map.isEmpty()) {
                    map.forEach(metadata::addUserMetadata);
                }
            });
            s3.putObject(path, fileName, inputStream, metadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Unable to store file to S3", e);
        }
    }

    public byte[] download(String path, String fileName) {
        try {
            S3Object object = s3.getObject(path, fileName);
            S3ObjectInputStream objectContent = object.getObjectContent();
            return IOUtils.toByteArray(objectContent);
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException(String.format("Unable to find the file [%s] in bucket [%s]", fileName, path), e);
        }
    }
}
