package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.model.Band;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    public List<Band> getAllBands(String username) {
        final String sql = "SELECT BAND_ID, BAND_NAME, BAND_PHOTO, USERNAME FROM band WHERE USERNAME = ?";
        return jdbcTemplate.query(
                sql,
                new Object[]{username},
                (resultSet,i) -> {
                    UUID id = UUID.fromString(resultSet.getString("BAND_ID"));
                    String name = resultSet.getString("BAND_NAME");
                    String photo_link = resultSet.getString("BAND_PHOTO");
                    String author = resultSet.getString("USERNAME");
                    return new Band(id, name, photo_link, author);
                });
    }

    @Override
    public void addBand(Band band) {
        final String sql = "INSERT INTO band(BAND_ID, BAND_NAME, BAND_PHOTO, USERNAME) VALUES(?,?,?,?)";
        jdbcTemplate.update(
                sql,
                new Object[]{
                        band.getBandId(),
                        band.getBandName(),
                        band.getBandPhoto().get(),
                        band.getUsername()
                });
    }

    @Override
    public Optional<Band> getBandById(UUID id) {
        final String sql = "SELECT BAND_ID, BAND_NAME, BAND_PHOTO, USERNAME FROM band WHERE BAND_ID = ?";
        Band band = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (resultSet, i) -> {
                    UUID bandId = UUID.fromString(resultSet.getString("BAND_ID"));
                    String name = resultSet.getString("BAND_NAME");
                    String photo_link = resultSet.getString("BAND_PHOTO");
                    String author = resultSet.getString("USERNAME");
                    return new Band(bandId, name, photo_link, author);
                });
        return Optional.ofNullable(band);
    }

    @Override
    public void deleteBandById(UUID id) {
        final String sql = "DELETE FROM band WHERE BAND_ID = ?";
        jdbcTemplate.update(
                sql,
                new Object[]{id});
    }

    @Override
    public void updateBandById(UUID id, Band band) {

    }

    @Override
    public boolean isBandExist(String bandName, String username) {
        final String sql = "SELECT USERNAME, BAND_NAME FROM band Where USERNAME = ? AND BAND_NAME = ?";
        List<String> result = new ArrayList<>();
        try {
            jdbcTemplate.query(
                    sql,
                    new Object[]{username, bandName},
                    resultSet -> {
                        result.add(resultSet.getString("USERNAME"));
                        result.add(resultSet.getString("BAND_NAME"));
                        return;
            });
        }catch (IncorrectResultSizeDataAccessException e){
            System.out.println("error");
        }
        if(result.size() == 0){
            return false;
        }
        return true;
    }
}
