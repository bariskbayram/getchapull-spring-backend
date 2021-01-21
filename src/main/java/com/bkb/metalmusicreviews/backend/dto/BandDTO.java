package com.bkb.metalmusicreviews.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BandDTO {

    @JsonProperty("band_name")
    private String bandName;

    @JsonProperty("band_spotify_id")
    private String bandSpotifyId;

}
