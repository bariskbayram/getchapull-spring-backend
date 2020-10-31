package com.bkb.metalmusicreviews.backend.api;

import com.bkb.metalmusicreviews.backend.model.Album;
import com.bkb.metalmusicreviews.backend.service.AlbumService;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/albums")
@CrossOrigin("*")
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('review:read')")
    public List<Album> getAllAlbums(@RequestParam(name = "username") String username){
        return albumService.getAllAlbums(username);
    }

    @GetMapping(path = "{albumId}/image/download")
    @PreAuthorize("hasAuthority('review:read')")
    public byte[] downloadAlbumImage(@PathVariable("albumId") UUID albumId,
                                     @RequestParam(name = "username") String username){
        byte[] arrayBase64 = Base64.getEncoder().encode(albumService.downloadAlbumImage(albumId, username));
        return arrayBase64;
    }

    //Çok fazla parametre var onun yerine direkt album nesnesi yollayıp @RequestBody ile Json çevirebiliriz.
    @PostMapping(
            path="/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('review:write')")
    public UUID uploadAlbum(@RequestParam("album_file") MultipartFile albumFile,
                            @RequestParam("album_title") String albumTitle,
                            @RequestParam("band_id") UUID bandId,
                            @RequestParam("year") String year,
                            @RequestParam("username") String username){

        return albumService.uploadAlbum(albumFile, albumTitle, bandId, year, username);
    }

    //orElse yerine 404 fırtlatman mantıklı olabilir bunu dene.
    @GetMapping(path = "{albumId}")
    @PreAuthorize("hasAuthority('review:read')")
    public Album getAlbumById(@PathVariable("albumId") UUID id){
        return albumService.getAlbumById(id).orElse(null);
    }

    @DeleteMapping(path = "{albumId}")
    @PreAuthorize("hasAuthority('review:write')")
    public void deleteAlbumById(@PathVariable("albumId") UUID id){
        albumService.deleteAlbumById(id);
    }

    @PutMapping(path = "{albumId}")
    @PreAuthorize("hasAuthority('review:write')")
    public void updateAlbumById(@PathVariable("albumId") UUID id, Album album){
        albumService.updateAlbumById(id, album);
    }

    @GetMapping(path = "/isExist/{albumName}")
    @PreAuthorize("hasAuthority('review:write')")
    public boolean isAlbumExistForSameUser(
            @PathVariable("albumName") String albumName,
            @RequestParam(name = "bandId") String bandId,
            @RequestParam(name = "username") String username){

        return albumService.isAlbumExistForSameUser(albumName, UUID.fromString(bandId), username);
    }

}

