package com.bkb.metalmusicreviews.backend.api;

import com.bkb.metalmusicreviews.backend.model.UserProfile;
import com.bkb.metalmusicreviews.backend.service.UserProfileService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasAuthority('member:read')")
    public List<UserProfile> getAllUserProfiles(){
        return userProfileService.getAllUserProfileS();
    }

    @GetMapping(
            path="/{username}/image/download",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    @PreAuthorize("hasAuthority('review:read')")
    public byte[] downloadProfilePhoto(@PathVariable("username") String username){
        byte[] array = userProfileService.downloadProfilePhoto(username);
        for(byte b: array){
            System.out.print(b);
        }
        return array;
    }

    @PostMapping("/signup")
    public void registerUser(@RequestBody UserProfile userProfile){
        userProfileService.registerUser(userProfile);
    }

    @GetMapping("/search-username")
    public boolean usernameIsAvailable(@RequestParam(name = "username") String username){
        return userProfileService.usernameIsAvailable(username);
    }
}
