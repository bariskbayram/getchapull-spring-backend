package com.bkb.metalmusicreviews.backend.service.interfaces;

import com.bkb.metalmusicreviews.backend.dto.AlbumDTO;
import com.bkb.metalmusicreviews.backend.entity.Album;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AlbumServiceInterface {

    List<Album> getAlbumsByUsername(String username);

    List<Album> getAlbumsByBandIdAndUsername(String username, int bandId);

    byte[] downloadAlbumImage(int albumId);

    Album findAlbumByAlbumSpotifyId(String albumSpotifyId);

    Album uploadAlbumFile(AlbumDTO albumDTO, MultipartFile file);

    int addAlbumForThisUser(int userId, int albumId);

    Optional<Album> getAlbumById(int albumId);

    int deleteAlbumByIdAndUserId(int albumId, int userId);

    int getAlbumCountByUsername(String username);

}
