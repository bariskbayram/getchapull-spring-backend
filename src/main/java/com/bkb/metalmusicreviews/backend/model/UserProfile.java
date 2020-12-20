package com.bkb.metalmusicreviews.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Array;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public class UserProfile implements UserDetails {

    private final String fullName;
    private final String username;
    private final String password;
    private final Set<? extends GrantedAuthority> grantedAuthorities;
    private Object friends = null;
    private int followers;
    private final boolean isAccountNonExpired;
    private final boolean isAccountNonLocked;
    private final boolean isCredentialsNonExpired;
    private final boolean isEnabled;

    public UserProfile(@JsonProperty("username") String username,
                       @JsonProperty("password") String password,
                       @JsonProperty("fullname") String fullName,
                       Set<? extends GrantedAuthority> grantedAuthorities,
                       boolean isAccountNonExpired,
                       boolean isAccountNonLocked,
                       boolean isCredentialsNonExpired,
                       boolean isEnabled) {

        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    public void setObjectFriend(Array friends, int followers) {
        try {
            this.friends = friends.getArray();
            this.followers = followers;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public String getFullName() {
        return fullName;
    }

    public Object getFriends() {
        return friends;
    }

    public int getFollowers() {
        return followers;
    }
}
