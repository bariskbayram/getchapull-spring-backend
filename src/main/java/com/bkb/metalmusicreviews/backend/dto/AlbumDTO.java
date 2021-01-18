package com.bkb.metalmusicreviews.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlbumDTO {

    @JsonProperty("album_name")
    private String albumName;

    @JsonProperty("album_spotify_id")
    private String albumSpotifyId;

    @JsonProperty("album_year")
    private int albumYear;

    @JsonProperty("band_id")
    private int bandId;

    @JsonProperty("user_id")
    private int userId;

    public AlbumDTO() {}

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumSpotifyId() {
        return albumSpotifyId;
    }

    public void setAlbumSpotifyId(String albumSpotifyId) {
        this.albumSpotifyId = albumSpotifyId;
    }

    public int getAlbumYear() {
        return albumYear;
    }

    public void setAlbumYear(int albumYear) {
        this.albumYear = albumYear;
    }

    public int getBandId() {
        return bandId;
    }

    public void setBandId(int bandId) {
        this.bandId = bandId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
