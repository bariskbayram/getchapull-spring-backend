package com.bkb.metalmusicreviews.backend.dao;

import com.amazonaws.services.apigateway.model.Op;
import com.bkb.metalmusicreviews.backend.model.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    public List<Album> getAllAlbums() {
        final String sql = "SELECT id,name, band, year, cover_link FROM album";
        return jdbcTemplate.query(
                sql,
                (resultSet,i) -> {
                    UUID id = UUID.fromString(resultSet.getString("id"));
                    String name = resultSet.getString("name");
                    String band = resultSet.getString("band");
                    String year = resultSet.getString("year");
                    String cover_link = resultSet.getString("cover_link");
                    return new Album(id, name, band, year, cover_link);
                });
    }

    @Override
    public void addAlbum(Album album) {
        final String sql = "INSERT INTO album(id, name, band, year, cover_link) VALUES (uuid_generate_v4(), ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                new Object[]{
                        album.getName(),
                        album.getBand(),
                        album.getYear(),
                        album.getCoverLink().get()
                });
    }

    @Override
    public Optional<Album> getAlbumById(UUID id) {
        final String sql = "SELECT id,name, band, year, cover_link FROM album Where id = ?";
        Album album = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (resultSet, i) -> {
                    UUID albumId = UUID.fromString(resultSet.getString("id"));
                    String name = resultSet.getString("name");
                    String band = resultSet.getString("band");
                    String year = resultSet.getString("year");
                    String cover_link = resultSet.getString("cover_link");
                    return new Album(albumId, name, band, year, cover_link);
                });

        return Optional.ofNullable(album);
    }

    @Override
    public int deleteAlbumById(UUID id) {
        return 0;
    }

    @Override
    public int updateAlbumById(UUID id, Album album) {
        return 0;
    }
}
