package com.bkb.metalmusicreviews.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewDTO {

    @JsonProperty("review_title")
    private String reviewTitle;

    @JsonProperty("review_content")
    private String reviewContent;

    @JsonProperty("review_point")
    private int reviewPoint;

    @JsonProperty("album_id")
    private int albumId;

    @JsonProperty("user_id")
    private int userId;

    public ReviewDTO() {}

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public int getReviewPoint() {
        return reviewPoint;
    }

    public void setReviewPoint(int reviewPoint) {
        this.reviewPoint = reviewPoint;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
