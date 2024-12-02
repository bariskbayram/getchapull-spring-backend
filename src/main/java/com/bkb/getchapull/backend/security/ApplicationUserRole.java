package com.bkb.getchapull.backend.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.bkb.getchapull.backend.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {

    NORMAL(Sets.newHashSet(REVIEW_READ, REVIEW_WRITE)),
    ADMIN(Sets.newHashSet(REVIEW_READ, REVIEW_WRITE, MEMBER_READ, MEMBER_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    //Authorities' type must be GrantedAuthority, so we should cast it
    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map( permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        //We also added the Role of user to the Set
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissions;
    }
}
