package com.bkb.metalmusicreviews.backend.model;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Band {

    private final UUID id;
    private final String name;
    private String photoLink;

    public Band(UUID id, String name, String photoLink) {
        this.id = id;
        this.name = name;
        this.photoLink = photoLink;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getPhotoLink() {
        return Optional.ofNullable(photoLink);
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,name, photoLink);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Band that = (Band) obj;
        return Objects.equals(id,that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(photoLink, that.photoLink);
    }
}
