package com.bkb.getchapull.backend.service.interfaces;

import com.bkb.getchapull.backend.dto.AlbumDTO;
import com.bkb.getchapull.backend.entity.Album;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AlbumServiceInterface {

    List<Album> getAlbumsByUsername(String username);

    List<Album> getAlbumsByBandIdAndUsername(String username, Long bandId);

    byte[] downloadAlbumImage(Long albumId);

    Album findAlbumByAlbumSpotifyId(String albumSpotifyId);

    Album uploadAlbumFile(AlbumDTO albumDTO, MultipartFile file);

    int  addAlbumForThisUser(Long userId, Long albumId);

    Optional<Album> getAlbumById(Long albumId);

    int deleteAlbumByIdAndUserId(Long albumId, Long userId);

    int getAlbumCountByUsername(String username);

}
