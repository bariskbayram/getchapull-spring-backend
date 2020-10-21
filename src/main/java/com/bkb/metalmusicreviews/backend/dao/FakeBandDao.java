package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.model.Band;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Sadece album için Dao implement ettim çünkü database geçilecek. Eğer lazım olursa ordan bak

@Repository("fakeBandDao")
public class FakeBandDao implements DataAccessBand {

    private static List<Band> bands = new ArrayList<>();

    @Override
    public List<Band> getAllBands() {
        return bands;
    }

    @Override
    public void addBand(Band band) {
        bands.add(band);
    }

    @Override
    public Optional<Band> getBandById(UUID id) {
        return Optional.empty();
    }

    @Override
    public void deleteBandById(UUID id) {

    }

    @Override
    public void updateBandById(UUID id, Band band) {

    }
}
