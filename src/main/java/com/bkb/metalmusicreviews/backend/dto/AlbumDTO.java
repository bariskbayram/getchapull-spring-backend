package com.bkb.metalmusicreviews.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlbumDTO {

    @JsonProperty("album_name")
    private String albumName;

    @JsonProperty("album_spotify_id")
    private String albumSpotifyId;

    @JsonProperty("album_year")
    private int albumYear;

    @JsonProperty("band_id")
    private int bandId;

    @JsonProperty("user_id")
    private int userId;

}
