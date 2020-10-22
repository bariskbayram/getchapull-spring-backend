package com.bkb.metalmusicreviews.backend.api;

import com.bkb.metalmusicreviews.backend.model.Album;
import com.bkb.metalmusicreviews.backend.service.AlbumService;
import com.bkb.metalmusicreviews.backend.service.BandService;
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
    private final BandService bandService;

    @Autowired
    public AlbumController(AlbumService albumService, BandService bandService) {
        this.albumService = albumService;
        this.bandService = bandService;
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
        return albumService.downloadAlbumImage(albumId, username);
    }

    //Çok fazla parametre var onun yerine direkt album nesnesi yollayıp @RequestBody ile Json çevirebiliriz.
    @PostMapping(
            path="/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('review:write')")
    public void uploadFilesAndReview(@RequestParam("album_file") MultipartFile albumFile,
                                     @RequestParam("band_file") MultipartFile bandFile,
                                     @RequestParam("album_title") String albumTitle,
                                     @RequestParam("band_name") String bandName,
                                     @RequestParam("year") String year,
                                     @RequestParam("username") String username){

        UUID bandId = bandService.uploadBandFile(bandFile, bandName, username);
        albumService.uploadAlbumFile(albumFile, albumTitle, bandId, year, username);
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

}

