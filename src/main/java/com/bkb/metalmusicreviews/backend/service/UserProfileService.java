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

import java.io.File;
import java.util.List;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return dataAccessUserProfile.getUserProfileByUsername(username)
                .orElseThrow(
                        () ->new UsernameNotFoundException(String.format("Username %s not found.", username)));
    }

    public void registerUser(UserProfile userProfile) {
        fileStoreService.createFolder(BucketName.IMAGE.getBucketName(), "profiles/" + userProfile.getUsername() + "/albums");
        fileStoreService.createFolder(BucketName.IMAGE.getBucketName(), "profiles/" + userProfile.getUsername() + "/bands");
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
}
