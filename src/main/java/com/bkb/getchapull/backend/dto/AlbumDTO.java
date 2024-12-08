package com.bkb.getchapull.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlbumDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("spotify_id")
    private String spotifyId;

    @JsonProperty("year")
    private int year;

    @JsonProperty("band_id")
    private Long bandId;

    @JsonProperty("user_id")
    private Long userId;

    public AlbumDTO(Long id, String name, String spotifyId, int year, Long bandId) {
        this.id = id;
        this.name = name;
        this.spotifyId = spotifyId;
        this.year = year;
        this.bandId = bandId;
    }
}
