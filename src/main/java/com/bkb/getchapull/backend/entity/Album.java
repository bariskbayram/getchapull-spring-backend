package com.bkb.getchapull.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Album")
@Table(
        name = "albums",
        uniqueConstraints = { @UniqueConstraint(name = "album_spotify_id_unique", columnNames = "album_spotify_id")}
)
public class Album {

    @Id
    @SequenceGenerator(
            name = "album_sequence",
            sequenceName = "album_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "album_sequence"
    )
    @Column(
            name = "album_id",
            updatable = false
    )
    private int albumId;

    @Column(
            name = "album_spotify_id",
            nullable = false,
            length = 62,
            unique = true
    )
    private String albumSpotifyId;

    @Column(
            name = "album_name",
            nullable = false,
            length = 100
    )
    private String albumName;

    @Column(
            name = "album_year",
            nullable = false
    )
    private int albumYear;

    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY
    )
    @JsonBackReference
    private Band band;

    @OneToMany(
            mappedBy = "album",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(
            mappedBy = "album",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<UserAlbum> userAlbums = new ArrayList<>();

    public Album(int albumId) {
        this.albumId = albumId;
    }

    public Album(String albumSpotifyId, String albumName, int albumYear) {
        this.albumSpotifyId = albumSpotifyId;
        this.albumName = albumName;
        this.albumYear = albumYear;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Album album = (Album) obj;
        return Objects.equals(albumSpotifyId, album.albumSpotifyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumSpotifyId);
    }
}
