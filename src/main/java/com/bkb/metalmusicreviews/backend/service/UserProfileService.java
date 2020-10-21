package com.bkb.metalmusicreviews.backend.service;

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
    public UserProfileService(@Qualifier("fakeUserProfileDao") DataAccessUserProfile dataAccessUserProfile, FileStoreService fileStoreService) {
        this.dataAccessUserProfile = dataAccessUserProfile;
        this.fileStoreService = fileStoreService;
    }


    public List<UserProfile> getAllUserProfileS() {
        return dataAccessUserProfile.getAllUserProfiles();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return dataAccessUserProfile.getUserProfileByUsername(username)
                .orElseThrow(
                        () ->new UsernameNotFoundException(String.format("Username %s not found.", username)));
    }
}
