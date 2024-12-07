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
        uniqueConstraints = { @UniqueConstraint(name = "spotify_id_unique", columnNames = "spotify_id")}
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
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "spotify_id",
            nullable = false,
            length = 62,
            unique = true
    )
    private String spotifyId;

    @Column(
            name = "name",
            nullable = false,
            length = 100
    )
    private String name;

    @Column(
            name = "year",
            nullable = false
    )
    private int year;

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

    public Album(Long id) {
        this.id = id;
    }

    public Album(String spotifyId, String name, int year) {
        this.spotifyId = spotifyId;
        this.name = name;
        this.year = year;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Album album = (Album) obj;
        return Objects.equals(spotifyId, album.spotifyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spotifyId);
    }
}
