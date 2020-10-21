package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.model.UserProfile;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.bkb.metalmusicreviews.backend.security.ApplicationUserRole.ADMIN;
import static com.bkb.metalmusicreviews.backend.security.ApplicationUserRole.NORMAL;

//Sadece album için Dao implement ettim çünkü database geçilecek. Eğer lazım olursa ordan bak

@Repository("fakeUserProfileDao")
public class FakeUserProfileDao implements DataAccessUserProfile{

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeUserProfileDao(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserProfile> getAllUserProfiles() {
        List<UserProfile> userProfiles = Lists.newArrayList(
                new UserProfile(
                        "Barış Kaan",
                        "Bayram",
                        "bariskbayram",
                        passwordEncoder.encode("password"),
                        ADMIN.getGrantedAuthorities(),
        true,
                        true,
                        true,
                        true,
                        ""
                ),
                new UserProfile(
                        "Kardelen",
                        "Kardan",
                        "kardelenkardan",
                        passwordEncoder.encode("password"),
                        NORMAL.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true,
                        ""
                )
        );

        return userProfiles;
    }

    @Override
    public void addUserProfile(UserProfile userProfile) {
        getAllUserProfiles().add(userProfile);
    }

    @Override
    public Optional<UserProfile> getUserProfileByUsername(String username) {
        return getAllUserProfiles()
                .stream()
                .filter( userProfile -> userProfile.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public void deleteUserProfileByUsername(String username) {

    }

    @Override
    public void updateUserProfileByUsername(String username, UserProfile userProfile) {

    }
}
