package com.bkb.metalmusicreviews.backend.service.interfaces;

import com.bkb.metalmusicreviews.backend.dto.UserDTO;
import com.bkb.metalmusicreviews.backend.entity.UserProfile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


public interface UserProfileServiceInterface {

    List<UserProfile> getAllUsers();

    List<UserProfile> getFollowers(int userId);

    List<UserProfile> getFollowings(int userId);

    List<UserProfile> getUserSuggestion(int userId);

    byte[] downloadProfilePhoto(String username);

    void uploadProfilePhoto(MultipartFile profilePhoto, String username);

    UserDetails loadUserByUsername(String username);

    void registerUser(UserDTO userDTO);

    void registerAdmin(UserDTO userDTO);

    boolean isUsernameExist(String username);

    boolean isEmailExist(String email);

    void deleteUserProfile(String username);

    void updateUserProfileByUsername(UserDTO userDTO);

    void followSomeone(Integer userId, Integer followingId);

    void unfollowSomeone(Integer userId, Integer unfollowingId);

    boolean isFollowedByUser(Integer userId, String otherUsername);
}
