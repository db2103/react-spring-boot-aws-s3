package com.db2112.awsimageupload.datastore;

import com.db2112.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FakeUserProfileDataStore {

    private static final List<UserProfile> USER_PROFILE_LIST = new ArrayList<>();

    static {
        USER_PROFILE_LIST.add(new UserProfile(UUID.fromString("1426860a-8f6c-4197-8ac9-94ba23718938"), "sam", null));
        USER_PROFILE_LIST.add(new UserProfile(UUID.fromString("d2b3a5c4-2e4d-4e95-a019-25a8f509e3f0"), "dean", null));
        USER_PROFILE_LIST.add(new UserProfile(UUID.fromString("142e1bb9-701b-4df3-8f7c-4c88b2b10863"), "mary", null));
    }

    public  List<UserProfile> getUserProfileList() {
        return USER_PROFILE_LIST;
    }
}
