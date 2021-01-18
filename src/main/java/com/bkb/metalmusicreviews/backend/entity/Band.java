package com.bkb.metalmusicreviews.backend.entity;

import java.util.Objects;

public class Band {

    private final int bandId;
    private final String bandSpotifyId;
    private final String bandName;

    public Band(int bandId, String bandSpotifyId, String bandName) {
        this.bandId = bandId;
        this.bandSpotifyId = bandSpotifyId;
        this.bandName = bandName;
    }

    public int getBandId() {
        return bandId;
    }

    public String getBandName() {
        return bandName;
    }

    public String getBandSpotifyId() {
        return bandSpotifyId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bandId, bandSpotifyId, bandName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Band that = (Band) obj;
        return Objects.equals(bandId,that.bandId) &&
                Objects.equals(bandSpotifyId, that.bandSpotifyId) &&
                Objects.equals(bandName, that.bandName);
    }
}
