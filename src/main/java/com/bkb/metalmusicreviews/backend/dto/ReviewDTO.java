package com.bkb.metalmusicreviews.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

}
