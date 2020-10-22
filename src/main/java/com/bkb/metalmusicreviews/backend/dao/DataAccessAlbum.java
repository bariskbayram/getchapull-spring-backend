package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.model.Album;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DataAccessAlbum {
    List<Album> getAllAlbums(String username);
    void addAlbum(Album album);
    Optional<Album> getAlbumById(UUID id);
    int deleteAlbumById(UUID id);
    int updateAlbumById(UUID id, Album album);
}
