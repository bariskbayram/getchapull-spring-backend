package com.bkb.metalmusicreviews.backend.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class UserProfile implements UserDetails {

    private final int userId;
    private final String username;
    private final String email;
    private final String password;
    private final String fullname;
    private final String bioInfo;
    private final String userCreated;
    private final Set<? extends GrantedAuthority> grantedAuthorities;
    private final boolean isAccountNonExpired;
    private final boolean isAccountNonLocked;
    private final boolean isCredentialsNonExpired;
    private final boolean isEnabled;

    public UserProfile(int userId,
                       String username,
                       String email,
                       String password,
                       String fullname,
                       String bioInfo,
                       String userCreated,
                       Set<? extends GrantedAuthority> grantedAuthorities,
                       boolean isAccountNonExpired,
                       boolean isAccountNonLocked,
                       boolean isCredentialsNonExpired,
                       boolean isEnabled) {

        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.bioInfo = bioInfo;
        this.userCreated = userCreated;
        this.grantedAuthorities = grantedAuthorities;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return fullname;
    }

    public String getBioInfo() {
        return bioInfo;
    }

    public String getUserCreated() {
        return userCreated;
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

}
