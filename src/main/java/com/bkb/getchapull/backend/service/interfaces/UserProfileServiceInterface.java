package com.bkb.getchapull.backend.service.interfaces;

import com.bkb.getchapull.backend.dto.UserDTO;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

public interface UserProfileServiceInterface {

    List<UserDTO> getAllUsers();

    List<UserDTO> findFollowersByUserId(Long userId);

    List<UserDTO> findFollowingsByUserId(Long userId);

    List<UserDTO> getUserSuggestion(Long userId);

    byte[] downloadProfilePhoto(String username);

    void uploadProfilePhoto(MultipartFile profilePhoto, String username);

    UserDetails loadUserByUsername(String username);

    UserDTO getUserByUsername(String username);

    void registerUser(UserDTO userDTO);

    void registerAdmin(UserDTO userDTO);

    boolean isUsernameExist(String username);

    boolean isEmailExist(String email);

    void deleteUserProfile(String username);

    void updateUserProfileByUsername(UserDTO userDTO);

    void followSomeone(Long followerId, Long followedId);

    void unfollowSomeone(Long unfollowerId, Long unfollowedId);

    boolean isFollowedByUser(Long userId, String otherUsername);
}
