package com.bkb.metalmusicreviews.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class PostDTO {

    private String postUsername;
    private int reviewId;
    private String reviewTitle;
    private String reviewContent;
    private int reviewPoint;
    private Timestamp postingDate;
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
            Timestamp postingDate,
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
}
