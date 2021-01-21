package com.bkb.metalmusicreviews.backend.api;

import com.bkb.metalmusicreviews.backend.dto.BandDTO;
import com.bkb.metalmusicreviews.backend.entity.Band;
import com.bkb.metalmusicreviews.backend.service.interfaces.BandServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bands")
@CrossOrigin("*")
public class BandController {

    private final BandServiceInterface bandService;

    @Autowired
    public BandController(@Qualifier("jpaServiceBand") BandServiceInterface bandService) {
        this.bandService = bandService;
    }

    @GetMapping("/get_bands_by_username")
    @PreAuthorize("hasAuthority('review:read')")
    public List<Band> getBandsByUsername(@RequestParam(name = "username") String username){
        return bandService.getBandsByUsername(username);
    }

    @GetMapping(path = "/download_band_image")
    @PreAuthorize("hasAuthority('review:read')")
    public byte[] downloadBandImage(@RequestParam(name = "band_id") int bandId){
        byte[] arrayBase64 = Base64.getEncoder().encode(bandService.downloadBandImage(bandId));
        return arrayBase64;
    }

    @PostMapping(
            path="/upload_band_with_image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('review:write')")
    public int uploadBand(@RequestPart("band_dto") BandDTO bandDTO, @RequestPart("multipart_file") MultipartFile file) {
        Band band = bandService.findBandByBandSpotifyId(bandDTO.getBandSpotifyId());
        if(band == null){
            return bandService.uploadBandFile(bandDTO, file).getBandId();
        }
        return band.getBandId();
    }

    //orElse yerine 404 fırtlatman mantıklı olabilir bunu dene.
    @GetMapping(path = "/get_band_by_id")
    @PreAuthorize("hasAuthority('review:read')")
    public Band getBandById(@RequestParam(name = "band_id") int bandId){
        return bandService.getBandById(bandId).orElse(null);
    }

    @GetMapping("/get_band_count_by_username")
    @PreAuthorize("hasAuthority('review:read')")
    public int getBandCountByUsername(@RequestParam(name = "username") String username){
        return bandService.getBandCountByUsername(username);
    }
}
