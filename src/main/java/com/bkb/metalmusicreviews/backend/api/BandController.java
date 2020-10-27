package com.bkb.metalmusicreviews.backend.api;

import com.bkb.metalmusicreviews.backend.model.Band;
import com.bkb.metalmusicreviews.backend.service.BandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/bands")
@CrossOrigin("*")
public class BandController {

    private final BandService bandService;

    @Autowired
    public BandController(BandService bandService) {
        this.bandService = bandService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('review:read')")
    public List<Band> getAllBands(@RequestParam(name = "username") String username){
        return bandService.getAllBands(username);
    }

    @GetMapping(path = "{bandId}/image/download")
    @PreAuthorize("hasAuthority('review:read')")
    public byte[] downloadBandImage(@PathVariable("bandId") UUID bandId,
                                    @RequestParam(name = "username") String username){
        byte[] arrayBase64 = Base64.getEncoder().encode(bandService.downloadBandImage(bandId, username));
        return arrayBase64;
    }

    @PostMapping(
            path="/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('review:write')")
    public UUID uploadBand(@RequestParam("band_file") MultipartFile bandFile,
                            @RequestParam("band_name") String bandName,
                            @RequestParam("username") String username) {


        return bandService.uploadBandFile(bandFile, bandName, username);
    }

    @DeleteMapping(path = "{bandId}")
    @PreAuthorize("hasAuthority('review:write')")
    public void deleteBandById(@PathVariable("bandId") UUID id){
        bandService.deleteBandById(id);
    }

    @GetMapping(path = "{bandName}")
    @PreAuthorize("hasAuthority('review:write')")
    public boolean isBandExist(
            @PathVariable("bandName") String bandName,
            @RequestParam(name = "username") String username){
        return bandService.isBandExist(bandName, username);
    }
}
