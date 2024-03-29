package com.bkb.metalmusicreviews.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@ToString
@Getter
@NoArgsConstructor
@Embeddable
public class UserAlbumId implements Serializable {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "album_id")
    private int albumId;

    public UserAlbumId(int userId, int albumId) {
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

