package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.model.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgresAlbum")
public class AlbumDataAccess implements DataAccessAlbum {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AlbumDataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Album> getAllAlbums(String username) {
        final String sql = "SELECT ALBUM_ID, ALBUM_NAME, BAND_ID, ALBUM_YEAR, ALBUM_COVER, USERNAME FROM album WHERE USERNAME = ?";
        return jdbcTemplate.query(
                sql,
                new Object[]{
                        username
                },
                (resultSet,i) -> {
                    UUID id = UUID.fromString(resultSet.getString("ALBUM_ID"));
                    String name = resultSet.getString("ALBUM_NAME");
                    UUID band_id = UUID.fromString(resultSet.getString("BAND_ID"));
                    String year = resultSet.getString("ALBUM_YEAR");
                    String cover_link = resultSet.getString("ALBUM_COVER");
                    String author = resultSet.getString("USERNAME");
                    return new Album(id, name, band_id, year, cover_link, author);
                });
    }

    @Override
    public void addAlbum(Album album) {
        final String sql = "INSERT INTO album(ALBUM_ID, ALBUM_NAME, BAND_ID, ALBUM_YEAR, ALBUM_COVER, USERNAME) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                new Object[]{
                        album.getId(),
                        album.getName(),
                        album.getBand(),
                        album.getYear(),
                        album.getCoverLink().get(),
                        album.getAuthor()
                });
    }

    @Override
    public Optional<Album> getAlbumById(UUID id) {
        final String sql = "SELECT ALBUM_ID, ALBUM_NAME, BAND_ID, ALBUM_YEAR, ALBUM_COVER, USERNAME FROM album Where ALBUM_ID = ?";
        Album album = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (resultSet, i) -> {
                    UUID album_id = UUID.fromString(resultSet.getString("ALBUM_ID"));
                    String name = resultSet.getString("ALBUM_NAME");
                    UUID band_id =  UUID.fromString(resultSet.getString("BAND_ID"));
                    String year = resultSet.getString("ALBUM_YEAR");
                    String cover_link = resultSet.getString("ALBUM_COVER");
                    String author = resultSet.getString("USERNAME");
                    return new Album(album_id, name, band_id, year, cover_link, author);
                });

        return Optional.ofNullable(album);
    }

    @Override
    public int deleteAlbumById(UUID id) {
        final String sql = "DELETE FROM album WHERE ALBUM_ID = ?";
        jdbcTemplate.update(
                sql,
                new Object[]{id});
        return 1;
    }

    @Override
    public int updateAlbumById(UUID id, Album album) {
        return 0;
    }

    @Override
    public boolean isAlbumExistForSameUser(String albumName, UUID bandId, String username) {

        final String sql = "SELECT ALBUM_ID, ALBUM_NAME, ALBUM_YEAR FROM album Where ALBUM_NAME = ? AND BAND_ID = ? AND USERNAME = ?";
        List<String> result = new ArrayList<>();
        try {
            jdbcTemplate.query(
                    sql,
                    new Object[]{albumName, bandId, username},
                    resultSet -> {
                        result.add(resultSet.getString("ALBUM_ID"));
                        result.add(resultSet.getString("ALBUM_NAME"));
                        result.add(resultSet.getString("ALBUM_YEAR"));
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

    @Override
    public List<Album> getAlbumByBandIdAndUsername(String username, UUID bandId) {
        final String sql = "SELECT ALBUM_ID, ALBUM_NAME, BAND_ID, ALBUM_YEAR, ALBUM_COVER, USERNAME FROM album WHERE USERNAME = ? AND BAND_ID = ?";
        return jdbcTemplate.query(
                sql,
                new Object[]{
                        username,
                        bandId
                },
                (resultSet,i) -> {
                    UUID id = UUID.fromString(resultSet.getString("ALBUM_ID"));
                    String name = resultSet.getString("ALBUM_NAME");
                    UUID band_id = UUID.fromString(resultSet.getString("BAND_ID"));
                    String year = resultSet.getString("ALBUM_YEAR");
                    String cover_link = resultSet.getString("ALBUM_COVER");
                    String author = resultSet.getString("USERNAME");
                    return new Album(id, name, band_id, year, cover_link, author);
                });
    }
}
