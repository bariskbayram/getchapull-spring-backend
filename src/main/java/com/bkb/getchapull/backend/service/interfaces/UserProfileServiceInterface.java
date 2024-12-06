package com.bkb.getchapull.backend.service.interfaces;

import com.bkb.getchapull.backend.dto.UserDTO;
import com.bkb.getchapull.backend.entity.UserProfile;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

public interface UserProfileServiceInterface {

    List<UserProfile> getAllUsers();

    List<UserProfile> getFollowers(Long userId);

    List<UserProfile> getFollowings(Long userId);

    List<UserProfile> getUserSuggestion(Long userId);

    byte[] downloadProfilePhoto(String username);

    void uploadProfilePhoto(MultipartFile profilePhoto, String username);

    UserDetails loadUserByUsername(String username);

    void registerUser(UserDTO userDTO);

    void registerAdmin(UserDTO userDTO);

    boolean isUsernameExist(String username);

    boolean isEmailExist(String email);

    void deleteUserProfile(String username);

    void updateUserProfileByUsername(UserDTO userDTO);

    void followSomeone(Long userId, Long followingId);

    void unfollowSomeone(Long userId, Long unfollowingId);

    boolean isFollowedByUser(Long userId, String otherUsername);
}
