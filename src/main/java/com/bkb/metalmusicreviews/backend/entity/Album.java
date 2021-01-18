package com.bkb.metalmusicreviews.backend.entity;

import java.util.Objects;

public class Album {

    private final int albumId;
    private final String albumSpotifyId;
    private final String albumName;
    private final int albumYear;
    private final int bandId;

    public Album(int albumId, String albumSpotifyId, String albumName, int albumYear, int bandId) {
        this.albumId = albumId;
        this.albumSpotifyId = albumSpotifyId;
        this.albumName = albumName;
        this.bandId = bandId;
        this.albumYear = albumYear;
    }

    public int getAlbumId() {
        return albumId;
    }

    public String getAlbumSpotifyId() {
        return albumSpotifyId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public int getBandId() {
        return bandId;
    }

    public int getAlbumYear() {
        return albumYear;
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumId, albumName, albumYear, bandId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Album that = (Album) obj;
        return Objects.equals(albumId,that.albumId) &&
                Objects.equals(albumName, that.albumName) &&
                Objects.equals(albumYear, that.albumYear) &&
                Objects.equals(bandId, that.bandId);
    }
}
