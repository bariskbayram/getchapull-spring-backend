package com.bkb.metalmusicreviews.backend.api;

import com.bkb.metalmusicreviews.backend.model.Band;
import com.bkb.metalmusicreviews.backend.service.BandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    @PreAuthorize("hasAuthority('review:read')")
    public List<Band> getAllBands(){
        return bandService.getAllBands();
    }

    @GetMapping(path = "{bandId}/image/download")
    @PreAuthorize("hasAuthority('review:read')")
    public byte[] downloadBandImage(@PathVariable("bandId") UUID bandId){
        return bandService.downloadBandImage(bandId);
    }
}
