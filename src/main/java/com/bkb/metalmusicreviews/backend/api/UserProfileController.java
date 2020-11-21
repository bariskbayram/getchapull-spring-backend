package com.bkb.metalmusicreviews.backend.api;

import java.util.Base64;
import com.bkb.metalmusicreviews.backend.model.UserProfile;
import com.bkb.metalmusicreviews.backend.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user-profiles")
@CrossOrigin("*")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public List<UserProfile> getAllUserProfiles(){
        return userProfileService.getAllUserProfileS();
    }

    @GetMapping(
            path="/{username}/image/download",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    @PreAuthorize("hasAuthority('review:read')")
    public byte[] downloadProfilePhoto(@PathVariable("username") String username){
        byte[] arrayBase64 = Base64.getEncoder().encode(userProfileService.downloadProfilePhoto(username));
        return arrayBase64;
    }

    @PostMapping(
            path = "/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('review:write')")
    public void uploadProfilePhoto(@RequestParam(value = "profile_photo") MultipartFile multipartFile,
                                   @RequestParam("username") String username){

        userProfileService.uploadProfilePhoto(username, multipartFile);

    }

    @GetMapping("/get-user")
    public UserDetails getUserByUsername(@RequestParam(name = "username") String username){
        return userProfileService.loadUserByUsername(username);
    }

    @PostMapping("/signup")
    public void registerUser(@RequestBody UserProfile userProfile){
        userProfileService.registerUser(userProfile);
    }

    @PostMapping("/admin/signup")
    public void registerAdmin(@RequestBody UserProfile userProfile){
        userProfileService.registerAdmin(userProfile);
    }

    @GetMapping("/search-username")
    public boolean usernameIsAvailable(@RequestParam(name = "username") String username){
        return userProfileService.usernameIsExist(username);
    }

    @DeleteMapping(path = "{username}")
    @PreAuthorize("hasAuthority('member:write')")
    public void deleteUserProfile(@PathVariable("username") String username){
        userProfileService.deleteUserProfile(username);
    }

    @PutMapping(path= "{username}")
    @PreAuthorize("hasAuthority('review:write')")
    public void updateUserProfile(@PathVariable("username") String username,
                                  @RequestBody UserProfile userProfile){
        userProfileService.updateUserProfieByUsername(username, userProfile);
    }

    @PutMapping(path = "/add-friend")
    @PreAuthorize("hasAuthority('review:write')")
    public void addFriend(@RequestParam("username") String username,
                          @RequestParam("friend_username") String friendUsername){
        userProfileService.addFriend(username, friendUsername);
    }
}
