package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.model.UserProfile;

import java.util.List;
import java.util.Optional;

public interface DataAccessUserProfile {
    List<UserProfile> getAllUserProfiles();
    void addUserProfile(UserProfile userProfile);
    Optional<UserProfile> getUserProfileByUsername(String username);
    void deleteUserProfileByUsername(String username);
    void updateUserProfileByUsername(String username, UserProfile userProfile);
    boolean usernameIsExist(String username);
    void addUserProfileForAdmin(UserProfile userProfile);
}
