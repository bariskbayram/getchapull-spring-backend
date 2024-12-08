package com.bkb.getchapull.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class PostDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("point")
    private int point;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    @JsonProperty("album_id")
    private Long albumId;

    @JsonProperty("album_name")
    private String albumName;

    @JsonProperty("band_id")
    private Long bandId;

    @JsonProperty("band_name")
    private String bandName;

    public PostDTO(
            Long id,
            String username,
            String title,
            String content,
            int point,
            OffsetDateTime createdAt,
            Long albumId,
            String albumName,
            Long bandId,
            String bandName) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.content = content;
        this.point = point;
        this.createdAt = createdAt;
        this.bandId = bandId;
        this.bandName = bandName;
        this.albumId = albumId;
        this.albumName = albumName;

    }
}
