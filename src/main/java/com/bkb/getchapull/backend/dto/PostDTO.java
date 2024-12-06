package com.bkb.getchapull.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class PostDTO {

    private String postUsername;
    private Long reviewId;
    private String reviewTitle;
    private String reviewContent;
    private int reviewPoint;
    private OffsetDateTime postingDate;
    private Long albumId;
    private String albumName;
    private Long bandId;
    private String bandName;

    public PostDTO(
            String postUsername,
            Long reviewId,
            String reviewTitle,
            String reviewContent,
            int reviewPoint,
            OffsetDateTime postingDate,
            Long albumId,
            String albumName,
            Long bandId,
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
