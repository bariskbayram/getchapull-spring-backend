package com.bkb.metalmusicreviews.backend.model;

import java.util.UUID;

public class Review {

    private final UUID reviewId;
    private final String title;
    private final String content;
    private final String point;
    private final UUID albumId;
    private final String username;

    public Review(
            UUID reviewId,
            String title,
            String content,
            String point,
            UUID albumId,
            String username) {
        this.reviewId = reviewId;
        this.title = title;
        this.content = content;
        this.point = point;
        this.albumId = albumId;
        this.username = username;
    }

    public UUID getReviewId() {
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

    public String getUsername() {
        return username;
    }
}
