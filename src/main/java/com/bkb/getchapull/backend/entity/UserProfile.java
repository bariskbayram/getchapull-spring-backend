package com.bkb.getchapull.backend.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "UserProfile")
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "email_unique", columnNames = "email"),
                @UniqueConstraint(name = "username_unique", columnNames = "username")
        }
)
public class UserProfile implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(
            name = "user_id",
            updatable = false
    )
    private Long userId;

    @Column(
            name = "username",
            nullable = false,
            length = 50,
            unique = true
    )
    private String username;

    @Column(
            name = "email",
            nullable = false,
            length = 100,
            unique = true
    )
    private String email;

    @Column(
            name = "password",
            nullable = false,
            length = 1000
    )
    private String password;

    @Column(
            name = "fullname",
            nullable = false,
            length = 80
    )
    private String fullname;

    @Column(
            name = "bio_info",
            length = 140
    )
    private String bioInfo;

    @Column(
            name = "user_created",
            nullable = false,
            updatable = false,
            insertable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP"
    )
    private OffsetDateTime userCreated;

    @Column(
            name = "user_role",
            nullable = false,
            length = 50
    )
    private String userRole;

    @OneToMany(
            mappedBy = "userProfile",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<UserAlbum> userAlbums = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<UserFollowing> userList = new ArrayList<>();

    @OneToMany(
            mappedBy = "following",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<UserFollowing> followingList = new ArrayList<>();

    @Transient
    private Set<? extends GrantedAuthority> grantedAuthorities;
    @Transient
    private boolean isAccountNonExpired;
    @Transient
    private boolean isAccountNonLocked;
    @Transient
    private boolean isCredentialsNonExpired;
    @Transient
    private boolean isEnabled;

    public UserProfile(Long userId) {
        this.userId = userId;
    }

    public UserProfile(String username, String email, String password, String fullname, String userRole) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(Set<? extends GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserProfile u = (UserProfile) obj;
        return Objects.equals(username, u.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
