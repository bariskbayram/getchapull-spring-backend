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
public class UserAlbumId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "album_id")
    private Long albumId;

    public UserAlbumId(Long userId, Long albumId) {
        this.userId = userId;
        this.albumId = albumId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        UserAlbumId that = (UserAlbumId) obj;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(albumId, that.albumId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId,albumId);
    }
}

