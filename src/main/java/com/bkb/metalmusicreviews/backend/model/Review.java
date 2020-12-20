package com.bkb.metalmusicreviews.backend.model;

import java.util.UUID;

public class Review {

    private final Integer reviewId;
    private final String title;
    private final String content;
    private final String point;
    private final UUID albumId;
    private final String albumName;
    private final UUID bandId;
    private final String bandName;
    private final String username;
    private final String date;

    public Review(
            Integer reviewId,
            String title,
            String content,
            String point,
            UUID albumId,
            String albumName,
            UUID bandId,
            String bandName,
            String username,
            String date) {
        this.reviewId = reviewId;
        this.title = title;
        this.content = content;
        this.point = point;
        this.albumId = albumId;
        this.albumName = albumName;
        this.bandId = bandId;
        this.bandName = bandName;
        this.username = username;
        this.date = date;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPoint() {
        return point;
    }

    public UUID getAlbumId() {
        return albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public UUID getBandId() {
        return bandId;
    }

    public String getBandName() {
        return bandName;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }
}
