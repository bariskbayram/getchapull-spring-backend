package com.bkb.metalmusicreviews.backend.service.implementations;

import com.bkb.metalmusicreviews.backend.bucket.BucketName;
import com.bkb.metalmusicreviews.backend.dto.UserDTO;
import com.bkb.metalmusicreviews.backend.entity.UserFollowing;
import com.bkb.metalmusicreviews.backend.entity.UserProfile;
import com.bkb.metalmusicreviews.backend.repository.UserProfileRepository;
import com.bkb.metalmusicreviews.backend.service.FileStoreService;
import com.bkb.metalmusicreviews.backend.service.interfaces.UserProfileServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

import static com.bkb.metalmusicreviews.backend.security.ApplicationUserRole.ADMIN;
import static com.bkb.metalmusicreviews.backend.security.ApplicationUserRole.NORMAL;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

@Service("jpaServiceUserProfile")
@Transactional
public class UserProfileServie implements UserProfileServiceInterface, UserDetailsService {

    private final UserProfileRepository userProfileRepository;
    private final FileStoreService fileStoreService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserProfileServie(@Qualifier("jpaRepoUserProfile") UserProfileRepository userProfileRepository, FileStoreService fileStoreService, PasswordEncoder passwordEncoder) {
        this.userProfileRepository = userProfileRepository;
        this.fileStoreService = fileStoreService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return userProfileRepository.findAll();
    }

    @Override
    public List<UserProfile> getFollowers(int userId) {
        return userProfileRepository.getFollowersByUserId(userId);
    }

    @Override
    public List<UserProfile> getFollowings(int userId) {
        return userProfileRepository.getFollowingsByUserId(userId);
    }

    @Override
    public List<UserProfile> getUserSuggestion(int userId) {
        return null;
    }

    @Override
    public byte[] downloadProfilePhoto(String username) {
        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(),
                "profilephotos");
        return fileStoreService.download(path, username + ".jpg");
    }

    @Override
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

        UserFollowing userFollowing = new UserFollowing(userProfile, userProfile);

        userProfile.setUserList(Arrays.asList(userFollowing));

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

        UserFollowing userFollowing = new UserFollowing(userProfile, userProfile);

        userProfile.setUserList(Arrays.asList(userFollowing));

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
    public void followSomeone(Integer userId, Integer followingId) {
        userProfileRepository.followSomeone(userId, followingId);
    }

    @Override
    public void unfollowSomeone(Integer userId, Integer unfollowingId) {
        userProfileRepository.unfollowSomeone(userId, unfollowingId);
    }

    @Override
    public boolean isFollowedByUser(Integer userId, String otherUsername) {
        String username = userProfileRepository.isFollowedByUser(userId, otherUsername);
        return username != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserProfile userProfile = userProfileRepository.findByUsername(username).get();

        Set<? extends GrantedAuthority> grantedAuthorities = null;
        if(userProfile.getUserRole().equals("ADMIN")){
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
}
