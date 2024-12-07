package com.bkb.getchapull.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Follow")
@Table(name = "follows")
public class Follow {

    @EmbeddedId
    private FollowId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("followerId")
    @JsonBackReference
    private UserProfile follower;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("followedId")
    @JsonBackReference
    private UserProfile followed;

    public Follow(UserProfile follower, UserProfile followed) {
        this.follower = follower;
        this.followed = followed;
        this.id = new FollowId(follower.getId(), followed.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Follow that = (Follow) obj;
        return Objects.equals(follower, that.follower) &&
                Objects.equals(followed, that.followed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(follower, followed);
    }
}

