package com.bkb.getchapull.backend.api;

import com.bkb.getchapull.backend.dto.BandDTO;
import com.bkb.getchapull.backend.entity.Band;
import com.bkb.getchapull.backend.service.interfaces.BandServiceInterface;

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

    @GetMapping("/find_bands_reviewed_by_user")
    @PreAuthorize("hasAuthority('review:read')")
    public List<BandDTO> findBandsReviewedByUser(@RequestParam(name = "username") String username){
        return bandService.findBandsReviewedByUser(username);
    }

    @GetMapping(path = "/download_band_image")
    @PreAuthorize("hasAuthority('review:read')")
    public byte[] downloadBandImage(@RequestParam(name = "band_id") Long bandId){
        return Base64.getEncoder().encode(bandService.downloadBandImage(bandId));
    }

    @PostMapping(
            path="/upload_band_with_image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('review:write')")
    public Long uploadBand(@RequestPart("band_dto") BandDTO bandDTO, @RequestPart("multipart_file") MultipartFile file) {
        Band band = bandService.findBandBySpotifyId(bandDTO.getSpotifyId());
        if( band == null) {
            return bandService.uploadBandFile(bandDTO, file).getId();
        }
        return band.getId();
    }

    //orElse yerine 404 fırtlatman mantıklı olabilir bunu dene.
    @GetMapping(path = "/get_band_by_id")
    @PreAuthorize("hasAuthority('review:read')")
    public Band getBandById(@RequestParam(name = "band_id") Long bandId){
        return bandService.getBandById(bandId).orElse(null);
    }

    @GetMapping("/get_reviewed_band_count_by_user")
    @PreAuthorize("hasAuthority('review:read')")
    public int getReviewedBandCountByUser(@RequestParam(name = "username") String username){
        return bandService.getReviewedBandCountByUser(username);
    }
}
