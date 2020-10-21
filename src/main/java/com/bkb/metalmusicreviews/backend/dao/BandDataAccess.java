package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.model.Band;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postresBand")
public class BandDataAccess implements DataAccessBand{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BandDataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Band> getAllBands() {
        final String sql = "SELECT id, name, photo_link FROM band";
        return jdbcTemplate.query(
                sql,
                (resultSet,i) -> {
                    UUID id = UUID.fromString(resultSet.getString("id"));
                    String name = resultSet.getString("name");
                    String photo_link = resultSet.getString("photo_link");
                    return new Band(id, name, photo_link);
                });
    }

    @Override
    public void addBand(Band band) {
        final String sql = "INSERT INTO band(id, name, photo_link) VALUES(uuid_generate_v4(),?,?)";
        jdbcTemplate.update(
                sql,
                new Object[]{
                        band.getName(),
                        band.getPhotoLink().get()
                });
    }

    @Override
    public Optional<Band> getBandById(UUID id) {
        final String sql = "SELECT id, name, photo_link FROM band WHERE id = ?";
        Band band = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (resultSet, i) -> {
                    UUID bandId = UUID.fromString(resultSet.getString("id"));
                    String name = resultSet.getString("name");
                    String photo_link = resultSet.getString("photo_link");
                    return new Band(bandId, name, photo_link);
                });
        return Optional.ofNullable(band);
    }

    @Override
    public void deleteBandById(UUID id) {

    }

    @Override
    public void updateBandById(UUID id, Band band) {

    }
}
