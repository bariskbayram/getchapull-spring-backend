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
        dataAccessUserProfile.addUserProfile(userProfile);
    }

    public boolean usernameIsAvailable(String username) {
        return dataAccessUserProfile.usernameIsAvailable(username);
    }
}
