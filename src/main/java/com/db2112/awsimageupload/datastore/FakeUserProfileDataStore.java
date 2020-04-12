package com.db2112.awsimageupload.datastore;

import com.db2112.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

    private static final List<UserProfile> USER_PROFILE_LIST = new ArrayList<>();

    static {
        USER_PROFILE_LIST.add(new UserProfile(UUID.randomUUID(), "sam", null));
        USER_PROFILE_LIST.add(new UserProfile(UUID.randomUUID(), "dean", null));
        USER_PROFILE_LIST.add(new UserProfile(UUID.randomUUID(), "mary", null));
    }

    public  List<UserProfile> getUserProfileList() {
        return USER_PROFILE_LIST;
    }
}
