package com.bkb.metalmusicreviews.backend.service.interfaces;

import com.bkb.metalmusicreviews.backend.dto.BandDTO;
import com.bkb.metalmusicreviews.backend.entity.Band;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

public interface BandServiceInterface {

    List<Band> getBandsByUsername(String username);

    byte[] downloadBandImage(int bandId);

    Band uploadBandFile(BandDTO bandDTO, MultipartFile file);

    Band findBandByBandSpotifyId(String bandSpotifyId);

    Optional<Band> getBandById(int bandId);

    int getBandCountByUsername(String username);

}
