package com.bkb.metalmusicreviews.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BandDTO {

    @JsonProperty("band_name")
    private String bandName;

    @JsonProperty("band_spotify_id")
    private String bandSpotifyId;

    public BandDTO() {}

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public String getBandSpotifyId() {
        return bandSpotifyId;
    }

    public void setBandSpotifyId(String bandSpotifyId) {
        this.bandSpotifyId = bandSpotifyId;
    }

}
