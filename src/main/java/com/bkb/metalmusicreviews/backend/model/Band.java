package com.bkb.metalmusicreviews.backend.model;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Band {

    private final UUID bandId;
    private final String bandName;
    private String bandPhoto;
    private String username;

    public Band(UUID bandId, String bandName, String bandPhoto, String username) {
        this.bandId = bandId;
        this.bandName = bandName;
        this.bandPhoto = bandPhoto;
        this.username = username;
    }

    public UUID getBandId() {
        return bandId;
    }

    public String getBandName() {
        return bandName;
    }

    public Optional<String> getBandPhoto() {
        return Optional.ofNullable(bandPhoto);
    }

    public void setBandPhoto(String bandPhoto) {
        this.bandPhoto = bandPhoto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bandId, bandName, bandPhoto, username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Band that = (Band) obj;
        return Objects.equals(bandId,that.bandId) &&
                Objects.equals(bandName, that.bandName) &&
                Objects.equals(username, that.username) &&
                Objects.equals(bandPhoto, that.bandPhoto);
    }
}
