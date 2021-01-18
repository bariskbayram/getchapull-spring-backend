package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.dto.BandDTO;
import com.bkb.metalmusicreviews.backend.entity.Band;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("postresBand")
public class BandDataAccess implements DataAccessBand{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BandDataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Band> getBandsByUsername(String username) {
        final String sql = "SELECT DISTINCT bands.band_id, bands.band_spotify_id, bands.band_name FROM bands INNER JOIN albums ON bands.band_id = albums.band_id INNER JOIN users_albums ON users_albums.album_id = albums.album_id INNER JOIN users ON users_albums.user_id = users.user_id WHERE users.username = ?";
        return jdbcTemplate.query(
                sql,
                new Object[]{username},
                (resultSet,i) -> {
                    int bandId = resultSet.getInt("band_id");
                    String bandSpotifyId = resultSet.getString("band_spotify_id");
                    String bandName = resultSet.getString("band_name");
                    return new Band(bandId, bandSpotifyId, bandName);
                });
    }

    @Override
    public int isBandExistBySpotifyId(String bandSpotifyId) {
        final String sql = "SELECT band_id FROM bands Where band_spotify_id = ?";
        List<Integer> result = new ArrayList<>();
        try {
            jdbcTemplate.query(
                    sql,
                    new Object[]{bandSpotifyId},
                    resultSet -> {
                        result.add(resultSet.getInt("band_id"));
                        return;
                    });
        }catch (IncorrectResultSizeDataAccessException e){
            System.out.println("error");
        }
        if(result.size() == 0){
            return -1;
        }
        System.out.println("bandId" + result.get(0));
        return result.get(0);
    }

    @Override
    public int addBand(BandDTO bandDTO) {
        final String sql = "INSERT INTO bands(band_spotify_id, band_name) VALUES (?, ?)";
        return jdbcTemplate.update(
                sql,
                new Object[]{
                        bandDTO.getBandSpotifyId(),
                        bandDTO.getBandName(),
                });
    }

    @Override
    public Optional<Band> getBandById(int inputBandId) {
        final String sql = "SELECT band_id, band_spotify_id, band_name FROM bands WHERE band_id = ?";
        Band band = jdbcTemplate.queryForObject(
                sql,
                new Object[]{inputBandId},
                (resultSet, i) -> {
                    int bandId = resultSet.getInt("band_id");
                    String bandSpotifyId = resultSet.getString("band_spotify_id");
                    String bandName = resultSet.getString("band_name");
                    return new Band(bandId, bandSpotifyId, bandName);
                });
        return Optional.ofNullable(band);
    }

    @Override
    public int getBandCountByUsername(String username) {
        final String sql = "SELECT count(DISTINCT bands.band_id) FROM bands INNER JOIN albums ON bands.band_id = albums.band_id INNER JOIN users_albums ON albums.album_id = users_albums.album_id WHERE users_albums.user_id IN (SELECT user_id FROM users WHERE username = ?)";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{
                        username
                },
                (resultSet,i) -> {
                    int count = resultSet.getInt("count");
                    return count;
                });
    }
}
