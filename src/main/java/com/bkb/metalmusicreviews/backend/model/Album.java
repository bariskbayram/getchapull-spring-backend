package com.bkb.metalmusicreviews.backend.model;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Album {

    private final UUID albumId;
    private final String albumName;
    private final UUID bandId;
    private final String albumYear;
    private String albumCover;
    private final String username;

    public Album(UUID albumId, String albumName, UUID bandId, String albumYear, String albumCover, String username) {
        this.albumId = albumId;
        this.albumName = albumName;
        this.bandId = bandId;
        this.albumYear = albumYear;
        this.albumCover = albumCover;
        this.username = username;
    }

    public UUID getId() {
        return albumId;
    }

    public String getName() {
        return albumName;
    }

    public UUID getBand() {
        return bandId;
    }

    public String getYear() {
        return albumYear;
    }

    public Optional<String> getCoverLink() {
        return Optional.ofNullable(albumCover);
    }

    public void setCoverLink(String coverLink){
        this.albumCover = coverLink;
    }

    public String getAuthor() {
        return username;
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumId, albumName, bandId, albumYear, albumCover, username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Album that = (Album) obj;
        return Objects.equals(albumId,that.albumId) &&
                Objects.equals(albumName, that.albumName) &&
                Objects.equals(bandId, that.bandId) &&
                Objects.equals(albumYear, that.albumYear) &&
                Objects.equals(albumCover, that.albumCover) &&
                Objects.equals(username, that.username);
    }
}
