package com.bkb.getchapull.backend.service.implementations;

import com.bkb.getchapull.backend.bucket.BucketName;
import com.bkb.getchapull.backend.dto.UserDTO;
import com.bkb.getchapull.backend.entity.Follow;
import com.bkb.getchapull.backend.entity.UserProfile;
import com.bkb.getchapull.backend.repository.UserProfileRepository;
import com.bkb.getchapull.backend.service.FileStoreService;
import com.bkb.getchapull.backend.service.interfaces.UserProfileServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.*;

import static com.bkb.getchapull.backend.security.ApplicationUserRole.ADMIN;
import static com.bkb.getchapull.backend.security.ApplicationUserRole.NORMAL;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

@Service("jpaServiceUserProfile")
@Transactional
public class UserProfileService implements UserProfileServiceInterface, UserDetailsService {

    private final UserProfileRepository userProfileRepository;
    private final FileStoreService fileStoreService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserProfileService(@Qualifier("jpaRepoUserProfile") UserProfileRepository userProfileRepository, FileStoreService fileStoreService, PasswordEncoder passwordEncoder) {
        this.userProfileRepository = userProfileRepository;
        this.fileStoreService = fileStoreService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return userProfileRepository.findAll();
    }

    @Override
    public List<UserProfile> getFollowers(Long userId) {
        return userProfileRepository.getFollowersByUserId(userId);
    }

    @Override
    public List<UserProfile> getFollowings(Long userId) {
        return userProfileRepository.getFollowingsByUserId(userId);
    }

    @Override
    public List<UserProfile> getUserSuggestion(Long userId) {
        return null;
    }

    @Override
    public byte[] downloadProfilePhoto(String username) {
        String key = String.format("profilephotos/%s", username + ".jpg");
        return fileStoreService.download(BucketName.IMAGE.getBucketName(), key);
    }

    @Override
    public void uploadProfilePhoto(MultipartFile profilePhoto, String username) {
        isImage(profilePhoto);
        isFileEmpty(profilePhoto);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", profilePhoto.getContentType());
        metadata.put("Content-Length", String.valueOf(profilePhoto.getSize()));

        String path = String.format("profilephotos/%s.jpg", username);

        try {
            fileStoreService.deleteImage(BucketName.IMAGE.getBucketName(),path);
            fileStoreService.save(BucketName.IMAGE.getBucketName(), path, Optional.of(metadata), profilePhoto.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void isImage(MultipartFile profilePhoto) {
        if(!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType()).contains(profilePhoto.getContentType())){
            throw new IllegalStateException("BandFile type is not correct! [" + profilePhoto.getContentType() + "]");
        }
    }

    private void isFileEmpty(MultipartFile profilePhoto) {
        if(profilePhoto.isEmpty()){
            throw new IllegalStateException("BandFile is empty!");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserProfile userProfile = userProfileRepository.findByUsername(username).get();

        Set<? extends GrantedAuthority> grantedAuthorities;
        if(userProfile.getRole().equals("ADMIN")){
            grantedAuthorities = ADMIN.getGrantedAuthorities();
        }else{
            grantedAuthorities = NORMAL.getGrantedAuthorities();
        }

        userProfile.setGrantedAuthorities(grantedAuthorities);
        userProfile.setEnabled(true);
        userProfile.setAccountNonExpired(true);
        userProfile.setAccountNonLocked(true);
        userProfile.setCredentialsNonExpired(true);
        return userProfile;
    }

    @Override
    public void registerUser(UserDTO userDTO) {
        fileStoreService.putProfilePhoto(
                BucketName.IMAGE.getBucketName(),
                "default.jpg",
                "profilephotos/" + userDTO.getUsername() + ".jpg");

        UserProfile userProfile = new UserProfile(
                userDTO.getUsername(),
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getFullname(),
                "NORMAL");

        Follow follow = new Follow(userProfile, userProfile);

        userProfile.setFollowing(Arrays.asList(follow));

        userProfileRepository.save(userProfile);
    }

    @Override
    public void registerAdmin(UserDTO userDTO) {
        fileStoreService.putProfilePhoto(
                BucketName.IMAGE.getBucketName(),
                "default.jpg",
                "profilephotos/" + userDTO.getUsername() + ".jpg");


        UserProfile userProfile = new UserProfile(
                userDTO.getUsername(),
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getFullname(),
                "ADMIN");

        Follow follow = new Follow(userProfile, userProfile);

        userProfile.setFollowing(Arrays.asList(follow));

        userProfileRepository.save(userProfile);
    }

    @Override
    public boolean isUsernameExist(String username) {
        return userProfileRepository.existsByUsername(username);
    }

    @Override
    public boolean isEmailExist(String email) {
        return userProfileRepository.existsByEmail(email);
    }

    @Override
    public void deleteUserProfile(String username) {
        userProfileRepository.deleteUserProfileByUsername(username);
    }

    @Override
    public void updateUserProfileByUsername(UserDTO userDTO) {
        userProfileRepository.updateUserProfile(userDTO.getFullname(), userDTO.getBioInfo(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getUsername());
    }

    @Override
    public void followSomeone(Long userId, Long followingId) {
        userProfileRepository.followSomeone(userId, followingId);
    }

    @Override
    public void unfollowSomeone(Long userId, Long unfollowingId) {
        userProfileRepository.unfollowSomeone(userId, unfollowingId);
    }

    @Override
    public boolean isFollowedByUser(Long userId, String otherUsername) {
        String username = userProfileRepository.isFollowedByUser(userId, otherUsername);
        return username != null;
    }
}
