package com.bkb.metalmusicreviews.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "UserFollowing")
@Table(name = "user_following")
public class UserFollowing {

    @EmbeddedId
    private UserFollowingId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("userId")
    @JsonBackReference
    private UserProfile user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("followingId")
    @JsonBackReference
    private UserProfile following;

    public UserFollowing(UserProfile user, UserProfile following) {
        this.user = user;
        this.following = following;
        this.id = new UserFollowingId(user.getUserId(), following.getUserId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        UserFollowing that = (UserFollowing) obj;
        return Objects.equals(user, that.user) &&
                Objects.equals(following, that.following);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, following);
    }
}

