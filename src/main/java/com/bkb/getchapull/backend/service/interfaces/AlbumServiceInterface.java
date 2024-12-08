package com.bkb.getchapull.backend.service.interfaces;

import com.bkb.getchapull.backend.dto.AlbumDTO;
import com.bkb.getchapull.backend.entity.Album;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AlbumServiceInterface {

    List<AlbumDTO> findAlbumsReviewedByUser(String username);

    List<AlbumDTO> findBandAlbumsReviewedByUser(String username, Long bandId);

    byte[] downloadAlbumImage(Long albumId);

    Album findAlbumBySpotifyId(String albumSpotifyId);

    Album uploadAlbumFile(AlbumDTO albumDTO, MultipartFile file);

    Optional<Album> getAlbumById(Long albumId);

    int getReviewedAlbumCountByUser(String username);

}
