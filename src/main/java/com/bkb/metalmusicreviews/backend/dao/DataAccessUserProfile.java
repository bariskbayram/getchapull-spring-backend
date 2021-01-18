package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.dto.UserDTO;
import com.bkb.metalmusicreviews.backend.entity.UserProfile;

import java.util.List;
import java.util.Optional;

public interface DataAccessUserProfile {

    List<UserProfile> getAllUsers();
    List<UserProfile> getFollowers(int userId);
    List<UserProfile> getFollowings(int userId);
    List<UserProfile> getUserSuggestion(int userId);
    Optional<UserProfile> loadUserByUsername(String username);
    void addUserProfile(UserDTO userDTO);
    void addUserProfileForAdmin(UserDTO userDTO);
    boolean isUsernameExist(String username);
    boolean isEmailExist(String email);
    void deleteUserProfileByUsername(String username);
    void updateUserProfileByUsername(UserDTO userDTO);
    void followSomeone(int userId, int followingId);
    void unfollowSomeone(int userId, int unfollowingId);
    boolean isYourFriend(int userId, String otherUsername);

}
