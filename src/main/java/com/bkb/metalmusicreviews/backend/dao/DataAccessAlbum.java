package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.dto.AlbumDTO;
import com.bkb.metalmusicreviews.backend.entity.Album;

import java.util.List;
import java.util.Optional;

public interface DataAccessAlbum {

    List<Album> getAlbumsByUsername(String username);
    List<Album> getAlbumsByBandIdAndUsername(String username, int bandId);
    int addAlbum(AlbumDTO albumDTO);
    int isAlbumExistBySpotifyId(String albumSpotifyId);
    int addAlbumForThisUser(int userId, int albumId);
    Optional<Album> getAlbumById(int id);
    int deleteAlbumByIdAndUserId(int albumId, int userId);
    int getAlbumCountByUsername(String username);
}
