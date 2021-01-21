package com.bkb.metalmusicreviews.backend.api;

import com.bkb.metalmusicreviews.backend.dto.AlbumDTO;
import com.bkb.metalmusicreviews.backend.entity.Album;

import java.util.Base64;

import com.bkb.metalmusicreviews.backend.service.interfaces.AlbumServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/albums")
@CrossOrigin("*")
public class AlbumController {

    private final AlbumServiceInterface albumService;

    @Autowired
    public AlbumController(@Qualifier("jpaServiceAlbum") AlbumServiceInterface albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/get_albums_by_username")
    @PreAuthorize("hasAuthority('review:read')")
    public List<Album> getAlbumsByUsername(@RequestParam(name = "username") String username){
        return albumService.getAlbumsByUsername(username);
    }

    @GetMapping("/get_albums_by_band_id_and_username")
    @PreAuthorize("hasAuthority('review:read')")
    public List<Album> getAlbumsByBandIdAndUsername(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "band_id") int bandId){
        return albumService.getAlbumsByBandIdAndUsername(username, bandId);
    }

    @GetMapping(path = "/download_album_image")
    @PreAuthorize("hasAuthority('review:read')")
    public byte[] downloadAlbumImage(@RequestParam(name = "album_id") int albumId){
        byte[] arrayBase64 = Base64.getEncoder().encode(albumService.downloadAlbumImage(albumId));
        return arrayBase64;
    }

    @PostMapping(
            path="/upload_album_with_image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('review:write')")
    public int uploadAlbum(@RequestPart("album_dto") AlbumDTO albumDTO, @RequestPart("multipart_file") MultipartFile file){
        Album album = albumService.findAlbumByAlbumSpotifyId(albumDTO.getAlbumSpotifyId());
        if(album == null){
            album = albumService.uploadAlbumFile(albumDTO, file);
        }
        albumService.addAlbumForThisUser(albumDTO.getUserId(), album.getAlbumId());
        return album.getAlbumId();
    }

    //orElse yerine 404 fırtlatman mantıklı olabilir bunu dene.
    @GetMapping(path = "/get_album_by_album_id")
    @PreAuthorize("hasAuthority('review:read')")
    public Album getAlbumById(@RequestParam(name = "album_id") int albumId){
        return albumService.getAlbumById(albumId).orElse(null);
    }

    @DeleteMapping(path = "/delete_album_by_album_id_for_user")
    @PreAuthorize("hasAuthority('review:write')")
    public int deleteAlbumByIdAndUserId(@RequestParam(name = "album_id") int albumId,
                                @RequestParam(name = "user_id") int userId){
        return albumService.deleteAlbumByIdAndUserId(albumId, userId);
    }

    @GetMapping("/get_album_count_by_username")
    @PreAuthorize("hasAuthority('review:read')")
    public int getAlbumCountByUserId(@RequestParam(name = "username") String username){
        return albumService.getAlbumCountByUsername(username);
    }

}

