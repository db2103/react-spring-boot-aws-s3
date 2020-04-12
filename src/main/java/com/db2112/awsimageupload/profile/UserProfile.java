package com.db2112.awsimageupload.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserProfile {
    private UUID userProfileId;
    private String userName;
    private String userProfileImageLink; // this will be the link to the file stored on S3
}
