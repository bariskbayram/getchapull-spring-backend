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
            name = "id",
            updatable = false
    )
    private Long id;

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
            name = "created_at",
            nullable = false,
            updatable = false,
            insertable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP"
    )
    private OffsetDateTime createdAt;

    @Column(
            name = "role",
            nullable = false,
            length = 50
    )
    private String role;

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
            mappedBy = "follower",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<Follow> following = new ArrayList<>();

    @OneToMany(
            mappedBy = "followed",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<Follow> followers = new ArrayList<>();

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

    public UserProfile(Long id) {
        this.id = id;
    }

    public UserProfile(String username, String email, String password, String fullname, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
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
