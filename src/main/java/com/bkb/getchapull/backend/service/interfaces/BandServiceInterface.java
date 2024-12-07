package com.bkb.getchapull.backend.service.interfaces;

import com.bkb.getchapull.backend.dto.BandDTO;
import com.bkb.getchapull.backend.entity.Band;

import org.springframework.web.multipart.MultipartFile;

import java.util.*;

public interface BandServiceInterface {

    List<Band> getBandsByUsername(String username);

    byte[] downloadBandImage(Long bandId);

    Band findBandBySpotifyId(String bandSpotifyId);

    Band uploadBandFile(BandDTO bandDTO, MultipartFile file);

    Optional<Band> getBandById(Long bandId);

    int getBandCountByUsername(String username);

}
