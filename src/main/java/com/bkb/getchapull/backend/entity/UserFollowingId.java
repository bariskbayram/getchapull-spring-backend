package com.bkb.getchapull.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@ToString
@Getter
@NoArgsConstructor
@Embeddable
public class UserFollowingId implements Serializable {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "following_id")
    private int followingId;

    public UserFollowingId(int userId, int followingId) {
        this.userId = userId;
        this.followingId = followingId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        UserFollowingId that = (UserFollowingId) obj;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(followingId, that.followingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId,followingId);
    }
}

