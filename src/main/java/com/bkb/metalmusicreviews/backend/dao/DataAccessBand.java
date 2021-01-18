package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.dto.BandDTO;
import com.bkb.metalmusicreviews.backend.entity.Band;

import java.util.List;
import java.util.Optional;

public interface DataAccessBand {

    List<Band> getBandsByUsername(String username);
    int addBand(BandDTO bandDTO);
    int isBandExistBySpotifyId(String bandSpotifyId);
    Optional<Band> getBandById(int bandId);
    int getBandCountByUsername(String username);
}
