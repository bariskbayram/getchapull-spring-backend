package com.bkb.getchapull.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.Objects;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "UserAlbum")
@Table(name = "user_album")
public class UserAlbum {

    @EmbeddedId
    private UserAlbumId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("userId")
    @JsonBackReference
    private UserProfile user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("albumId")
    @JsonBackReference
    private Album album;

    public UserAlbum(UserProfile user, Album album) {
        this.user = user;
        this.album = album;
        this.id = new UserAlbumId(user.getId(), album.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        UserAlbum that = (UserAlbum) obj;
        return Objects.equals(user, that.user) &&
                Objects.equals(album, that.album);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, album);
    }
}

