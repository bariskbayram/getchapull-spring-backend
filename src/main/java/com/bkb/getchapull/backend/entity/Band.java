package com.bkb.getchapull.backend.entity;

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
@Entity(name = "Band")
@Table(
        name = "bands",
        uniqueConstraints = { @UniqueConstraint(name = "band_spotify_id_unique", columnNames = "spotify_id")}
)
public class Band {

    @Id
    @SequenceGenerator(
            name = "band_sequence",
            sequenceName = "band_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "band_sequence"
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

    @OneToMany(
            mappedBy = "band",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<Album> albums = new ArrayList<>();

    public Band(Long id) {
        this.id = id;
    }

    public Band(String spotifyId, String name) {
        this.spotifyId = spotifyId;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Band band = (Band) obj;
        return Objects.equals(spotifyId, band.spotifyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spotifyId);
    }

}
