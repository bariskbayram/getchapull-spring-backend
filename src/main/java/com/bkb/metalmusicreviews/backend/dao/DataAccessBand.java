package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.model.Band;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DataAccessBand {
    List<Band> getAllBands(String username);
    void addBand(Band band);
    Optional<Band> getBandById(UUID id);
    void deleteBandById(UUID id);
    void updateBandById(UUID id, Band band);
}
