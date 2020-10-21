package com.bkb.metalmusicreviews.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Album {

    private final UUID id;
    private final String name;
    private final String band;
    private final String year;
    private String coverLink;

    public Album(UUID id, String band, String name, String year, String coverLink) {
        this.id = id;
        this.name = name;
        this.band = band;
        this.year = year;
        this.coverLink = coverLink;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBand() {
        return band;
    }

    public String getYear() {
        return year;
    }

    public Optional<String> getCoverLink() {
        return Optional.ofNullable(coverLink);
    }

    public void setCoverLink(String coverLink){
        this.coverLink = coverLink;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,name, coverLink);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Album that = (Album) obj;
        return Objects.equals(id,that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(coverLink, that.coverLink);
    }
}
