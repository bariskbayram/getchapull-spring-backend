package com.bkb.metalmusicreviews.backend.dto;

public class PostDTO {

    private String postUsername;
    private int reviewId;
    private String reviewTitle;
    private String reviewContent;
    private int reviewPoint;
    private String postingDate;
    private int albumId;
    private String albumName;
    private int bandId;
    private String bandName;

    public PostDTO(
            String postUsername,
            int reviewId,
            String reviewTitle,
            String reviewContent,
            int reviewPoint,
            String postingDate,
            int albumId,
            String albumName,
            int bandId,
            String bandName) {

        this.postUsername = postUsername;
        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewPoint = reviewPoint;
        this.postingDate = postingDate;
        this.bandId = bandId;
        this.bandName = bandName;
        this.albumId = albumId;
        this.albumName = albumName;

    }

    public String getPostUsername() {
        return postUsername;
    }

    public void setPostUsername(String postUsername) {
        this.postUsername = postUsername;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

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

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public int getBandId() {
        return bandId;
    }

    public void setBandId(int bandId) {
        this.bandId = bandId;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
}
