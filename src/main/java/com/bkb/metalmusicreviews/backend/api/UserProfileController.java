package com.bkb.metalmusicreviews.backend.api;

import java.util.Base64;

import com.bkb.metalmusicreviews.backend.dto.UserDTO;
import com.bkb.metalmusicreviews.backend.entity.UserProfile;
import com.bkb.metalmusicreviews.backend.service.interfaces.UserProfileServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
public class UserProfileController {

    private final UserProfileServiceInterface userProfileService;

    @Autowired
    public UserProfileController(@Qualifier("jpaServiceUserProfile") UserProfileServiceInterface userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/get_all_users")
    public List<UserProfile> getAllUsers(){
        return userProfileService.getAllUsers();
    }

    @GetMapping("/get_followers")
    @PreAuthorize("hasAuthority('review:write')")
    public List<UserProfile> getFollowers(@RequestParam(name = "user_id") int userId){
        return userProfileService.getFollowers(userId);
    }

    @GetMapping("/get_followings")
    @PreAuthorize("hasAuthority('review:write')")
    public List<UserProfile> getFollowings(@RequestParam(name = "user_id") int userId){
        return userProfileService.getFollowings(userId);
    }

    @GetMapping("/get_five_user_suggestion")
    @PreAuthorize("hasAuthority('review:write')")
    public List<UserProfile> getUserSuggestion(@RequestParam(name = "user_id") int userId){
        return userProfileService.getUserSuggestion(userId);
    }

    @GetMapping(
            path="/download_profile_photo",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    @PreAuthorize("hasAuthority('review:read')")
    public byte[] downloadProfilePhoto(@RequestParam(name = "username") String username){
        byte[] arrayBase64 = Base64.getEncoder().encode(userProfileService.downloadProfilePhoto(username));
        return arrayBase64;
    }

    @PostMapping(
            path = "/upload_profile_photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('review:write')")
    public void uploadProfilePhoto(@RequestParam(name = "profile_photo") MultipartFile multipartFile,
                                   @RequestParam(name = "username") String username){

        userProfileService.uploadProfilePhoto(username, multipartFile);

    }

    @GetMapping("/get_user_by_username")
    public UserDetails getUserByUsername(@RequestParam(name = "username") String username){
        return userProfileService.loadUserByUsername(username);
    }

    @PostMapping("/signup")
    public void registerUser(@RequestBody UserDTO userDTO){
        userProfileService.registerUser(userDTO);
    }

    @PostMapping("/admin/signup")
    public void registerAdmin(@RequestBody UserDTO userDTO){
        userProfileService.registerAdmin(userDTO);
    }

    @GetMapping("/check_username_exist")
    public boolean isUsernameExist(@RequestParam(name = "username") String username){
        return userProfileService.isUsernameExist(username);
    }

    @GetMapping("/check_email_exist")
    public boolean isEmailExist(@RequestParam(name = "email") String email){
        return userProfileService.isEmailExist(email);
    }

    @DeleteMapping(path = "/delete_user_by_username")
    @PreAuthorize("hasAuthority('member:write')")
    public void deleteUserProfile(@RequestParam(name = "username") String username){
        userProfileService.deleteUserProfile(username);
    }

    @PutMapping("/update_user_by_username")
    @PreAuthorize("hasAuthority('review:write')")
    public void updateUserProfile(@RequestBody UserDTO userDTO){
        userProfileService.updateUserProfileByUsername(userDTO);
    }

    @PutMapping(path = "/follow_someone")
    @PreAuthorize("hasAuthority('review:write')")
    public void followSomeone(@RequestParam(name = "user_id") Integer userId,
                          @RequestParam(name = "following_id") Integer followingId){
        userProfileService.followSomeone(userId, followingId);
    }

    @PutMapping(path = "/unfollow_someone")
    @PreAuthorize("hasAuthority('review:write')")
    public void unfollowSomeone(@RequestParam(name = "user_id") Integer userId,
                          @RequestParam(name = "unfollowing_id") Integer unfollowingId){
        userProfileService.unfollowSomeone(userId, unfollowingId);
    }

    @GetMapping(path = "/is_followed_by_user")
    @PreAuthorize("hasAuthority('review:write')")
    public boolean isFollowedByUser(@RequestParam("user_id") Integer userId,
                             @RequestParam("other_username") String otherUsername){
        return userProfileService.isFollowedByUser(userId, otherUsername);
    }
}
