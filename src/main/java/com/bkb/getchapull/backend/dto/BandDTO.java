package com.bkb.getchapull.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BandDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("spotify_id")
    private String spotifyId;

    public BandDTO(Long id, String name, String spotifyId) {
        this.id = id;
        this.name = name;
        this.spotifyId = spotifyId;
    }
}
