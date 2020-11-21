package com.bkb.metalmusicreviews.backend.service;

import com.bkb.metalmusicreviews.backend.bucket.BucketName;
import com.bkb.metalmusicreviews.backend.dao.DataAccessUserProfile;
import com.bkb.metalmusicreviews.backend.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    public List<UserProfile> getAllUserProfileS() {
        return dataAccessUserProfile.getAllUserProfiles();
    }

    public byte[] downloadProfilePhoto(String username) {
        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(),
                "profiles/" + username + "/profilephotos");
        return fileStoreService.download(path, username + ".jpg");
    }

    public void uploadProfilePhoto(String username, MultipartFile profilePhoto) {
        isImage(profilePhoto);
        isFileEmpty(profilePhoto);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", profilePhoto.getContentType());
        metadata.put("Content-Length", String.valueOf(profilePhoto.getSize()));

        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(), "profiles/" + username + "/profilephotos");

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
        return dataAccessUserProfile.getUserProfileByUsername(username)
                .orElseThrow(
                        () ->new UsernameNotFoundException(String.format("Username %s not found.", username)));
    }

    public void registerUser(UserProfile userProfile) {
        fileStoreService.createFolder(BucketName.IMAGE.getBucketName(), "profiles/" + userProfile.getUsername() + "/albums/");
        fileStoreService.createFolder(BucketName.IMAGE.getBucketName(), "profiles/" + userProfile.getUsername() + "/bands/");
        fileStoreService.putProfilePhoto(
                BucketName.IMAGE.getBucketName(),
                "profiles/" + userProfile.getUsername() + "/profilephotos/" + userProfile.getUsername() + ".jpg",
                new File("src/main/resources/static/default.jpg"));
        dataAccessUserProfile.addUserProfile(userProfile);
    }

    public boolean usernameIsExist(String username) {
        return dataAccessUserProfile.usernameIsExist(username);
    }

    public void registerAdmin(UserProfile userProfile) {
        fileStoreService.createFolder(BucketName.IMAGE.getBucketName(), "profiles/" + userProfile.getUsername() + "/albums");
        fileStoreService.createFolder(BucketName.IMAGE.getBucketName(), "profiles/" + userProfile.getUsername() + "/bands");
        fileStoreService.putProfilePhoto(
                BucketName.IMAGE.getBucketName(),
                "profiles/" + userProfile.getUsername() + "/profilephotos/" + userProfile.getUsername() + ".jpg",
                new File("src/main/resources/static/default.jpg"));
        dataAccessUserProfile.addUserProfileForAdmin(userProfile);
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

    public void updateUserProfieByUsername(String username, UserProfile userProfile) {
        dataAccessUserProfile.updateUserProfileByUsername(username, userProfile);
    }

    public void addFriend(String username, String friendUsername) {
        dataAccessUserProfile.addFriend(username, friendUsername);
    }
}
