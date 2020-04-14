package com.db2112.awsimageupload.profile;

import com.db2112.awsimageupload.bucket.BucketName;
import com.db2112.awsimageupload.filestore.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;


    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    public List<UserProfile> getUserProfiles() {
        return userProfileDataAccessService.getUserProfiles();
    }

    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) throws Exception {
        // 1. Check if file is not empty
        isFileEmpty(file);
        // 2. Check if file is an image
        isAnImage(file);

        // 3. Check if user exists
        UserProfile userProfile = getUserProfileDataOrThrow(userProfileId);
        // 4. Create MetaData
        Map<String, String> metaData = extractMetaData(file);

        // 5. Upload the file to S3 and update DB
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), userProfileId);
        String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        fileStore.save(path, fileName, Optional.of(metaData), file.getInputStream());
        userProfile.setUserProfileImageLink(fileName);

    }


    public byte[] downloadProfileImage(UUID userProfileId) {
        // 1. Check if the user exists
        UserProfile userProfile = getUserProfileDataOrThrow(userProfileId);

        // 2. Form the usr to fetch data from s3
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), userProfileId);
        String fileName = userProfile.getUserProfileImageLink();

        if(fileName != null){
            return fileStore.download(path, fileName);
        }
        throw new IllegalStateException("No file to download");
    }

    private void isAnImage(MultipartFile file) {
        if (!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(), ContentType.IMAGE_PNG.getMimeType(), ContentType.IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File format not supported. It should be an image");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalStateException("File cannot be empty");
        }
    }

    private Map<String, String> extractMetaData(MultipartFile file) {
        Map<String, String> metaData = new HashMap<>();
        metaData.put("Content-Type", file.getContentType());
        metaData.put("Content-Length", String.valueOf(file.getSize()));
        return metaData;
    }

    private UserProfile getUserProfileDataOrThrow(UUID userProfileId) {
        return userProfileDataAccessService.getUserProfiles()
                .stream()
                .filter(u -> Objects.equals(u.getUserProfileId(), userProfileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("User not found %s", userProfileId)));
    }

}
