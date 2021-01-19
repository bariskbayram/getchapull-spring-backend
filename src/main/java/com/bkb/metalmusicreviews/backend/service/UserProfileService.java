package com.bkb.metalmusicreviews.backend.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.bkb.metalmusicreviews.backend.bucket.BucketName;
import com.bkb.metalmusicreviews.backend.dao.DataAccessUserProfile;
import com.bkb.metalmusicreviews.backend.dto.UserDTO;
import com.bkb.metalmusicreviews.backend.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

@Service
public class UserProfileService implements UserDetailsService {
    private final DataAccessUserProfile dataAccessUserProfile;

    private final FileStoreService fileStoreService;

    @Autowired
    public UserProfileService(@Qualifier("postgresUser") DataAccessUserProfile dataAccessUserProfile, FileStoreService fileStoreService) {
        this.dataAccessUserProfile = dataAccessUserProfile;
        this.fileStoreService = fileStoreService;
    }

    public List<UserProfile> getAllUsers() {
        return dataAccessUserProfile.getAllUsers();
    }

    public List<UserProfile> getFollowers(int userId) {
        return dataAccessUserProfile.getFollowers(userId);
    }

    public List<UserProfile> getFollowings(int userId) {
        return dataAccessUserProfile.getFollowings(userId);
    }

    public List<UserProfile> getUserSuggestion(int userId) {
        return dataAccessUserProfile.getUserSuggestion(userId);
    }

    public byte[] downloadProfilePhoto(String username) {
        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(),
                "profilephotos");
        return fileStoreService.download(path, username + ".jpg");
    }

    public void uploadProfilePhoto(String username, MultipartFile profilePhoto) {
        isImage(profilePhoto);
        isFileEmpty(profilePhoto);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", profilePhoto.getContentType());
        metadata.put("Content-Length", String.valueOf(profilePhoto.getSize()));

        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(), "profilephotos");

        String filename = String.format("%s.jpg", username);

        try {
            fileStoreService.deleteImage(path,filename);
            fileStoreService.save(path, filename, Optional.of(metadata), profilePhoto.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return dataAccessUserProfile.loadUserByUsername(username)
                .orElseThrow(
                        () ->new UsernameNotFoundException(String.format("Username %s not found.", username)));
    }

    public void registerUser(UserDTO userDTO) {
        fileStoreService.putProfilePhoto(
                BucketName.IMAGE.getBucketName(),
                "default.jpg",
                "profilephotos/" + userDTO.getUsername() + ".jpg");
        dataAccessUserProfile.addUserProfile(userDTO);
    }

    public void registerAdmin(UserDTO userDTO) {
        fileStoreService.putProfilePhoto(
                BucketName.IMAGE.getBucketName(),
                "default.jpg",
                "profilephotos/" + userDTO.getUsername() + ".jpg");
        dataAccessUserProfile.addUserProfileForAdmin(userDTO);
    }

    public boolean isUsernameExist(String username) {
        return dataAccessUserProfile.isUsernameExist(username);
    }

    public boolean isEmailExist(String email) {
        return dataAccessUserProfile.isEmailExist(email);
    }

    public void deleteUserProfile(String username) {
        dataAccessUserProfile.deleteUserProfileByUsername(username);
    }

    private void isFileEmpty(MultipartFile profilePhoto) {
        if(!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType()).contains(profilePhoto.getContentType())){
            throw new IllegalStateException("BandFile type is not correct! [" + profilePhoto.getContentType() + "]");
        }
    }

    private void isImage(MultipartFile profilePhoto) {
        if(profilePhoto.isEmpty()){
            throw new IllegalStateException("BandFile is empty!");
        }
    }

    public void updateUserProfieByUsername(UserDTO userDTO) {
        dataAccessUserProfile.updateUserProfileByUsername(userDTO);
    }

    public void followSomeone(Integer userId, Integer followingId) {
        dataAccessUserProfile.followSomeone(userId, followingId);
    }

    public void unfollowSomeone(Integer userId, Integer unfollowingId) {
        dataAccessUserProfile.unfollowSomeone(userId, unfollowingId);
    }

    public boolean isYourFriend(Integer userId, String otherUsername) {
        return dataAccessUserProfile.isYourFriend(userId, otherUsername);
    }
}
