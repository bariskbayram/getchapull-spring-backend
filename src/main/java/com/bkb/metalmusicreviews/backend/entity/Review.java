package com.bkb.metalmusicreviews.backend.entity;

import java.util.Objects;

public class Review {

    private final int reviewId;
    private final String reviewTitle;
    private final String reviewContent;
    private final int reviewPoint;
    private final String postingDate;
    private final int albumId;
    private final int userId;

    public Review(
            int reviewId,
            String reviewTitle,
            String reviewContent,
            int reviewPoint,
            String postingDate,
            int albumId,
            int userId) {

        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewPoint = reviewPoint;
        this.albumId = albumId;
        this.userId = userId;
        this.postingDate = postingDate;

    }

    public int getReviewId() {
        return reviewId;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public int getReviewPoint() {
        return reviewPoint;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public int getAlbumId() {
        return albumId;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId, reviewTitle, reviewContent, reviewPoint, postingDate, albumId, userId);
    }
}
