package com.bkb.getchapull.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDTO {

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("point")
    private int point;

    @JsonProperty("album_id")
    private Long albumId;

    @JsonProperty("user_id")
    private Long userId;

}
