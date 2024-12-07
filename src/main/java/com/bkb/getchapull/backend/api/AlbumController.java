package com.bkb.getchapull.backend.api;

import com.bkb.getchapull.backend.dto.AlbumDTO;
import com.bkb.getchapull.backend.entity.Album;
import com.bkb.getchapull.backend.service.interfaces.AlbumServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
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
            @RequestParam(name = "band_id") Long bandId){
        return albumService.getAlbumsByBandIdAndUsername(username, bandId);
    }

    @GetMapping(path = "/download_album_image")
    @PreAuthorize("hasAuthority('review:read')")
    public byte[] downloadAlbumImage(@RequestParam(name = "album_id") Long albumId){
        byte[] arrayBase64 = Base64.getEncoder().encode(albumService.downloadAlbumImage(albumId));
        return arrayBase64;
    }

    @PostMapping(
            path="/upload_album_with_image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('review:write')")
    public Long uploadAlbum(@RequestPart("album_dto") AlbumDTO albumDTO, @RequestPart("multipart_file") MultipartFile file){
        Album album = albumService.findAlbumBySpotifyId(albumDTO.getAlbumSpotifyId());
        if(album == null){
            album = albumService.uploadAlbumFile(albumDTO, file);
        }
        albumService.addAlbumForThisUser(albumDTO.getUserId(), album.getId());
        return album.getId();
    }

    //orElse yerine 404 fırtlatman mantıklı olabilir bunu dene.
    @GetMapping(path = "/get_album_by_album_id")
    @PreAuthorize("hasAuthority('review:read')")
    public Album getAlbumById(@RequestParam(name = "album_id") Long albumId){
        return albumService.getAlbumById(albumId).orElse(null);
    }

    @DeleteMapping(path = "/delete_album_by_album_id_for_user")
    @PreAuthorize("hasAuthority('review:write')")
    public int deleteAlbumByIdAndUserId(@RequestParam(name = "album_id") Long albumId,
                                @RequestParam(name = "user_id") Long userId){
        return albumService.deleteAlbumByIdAndUserId(albumId, userId);
    }

    @GetMapping("/get_album_count_by_username")
    @PreAuthorize("hasAuthority('review:read')")
    public int getAlbumCountByUsername(@RequestParam(name = "username") String username){
        return albumService.getAlbumCountByUsername(username);
    }

}

