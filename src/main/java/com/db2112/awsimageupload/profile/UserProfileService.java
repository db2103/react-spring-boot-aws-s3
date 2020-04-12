package com.db2112.awsimageupload.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;


    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService) {
        this.userProfileDataAccessService = userProfileDataAccessService;
    }

    public List<UserProfile> getUserProfiles() {
        return userProfileDataAccessService.getUserProfiles();
    }

    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        // 1. Check if file is not empty
        // 2. Check if file is an image
        // 3. Check if user exists
        // 4. Check if metadata exists for the file
        // 5. Upload the file to S3 and update DB
    }
}
