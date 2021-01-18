package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.dto.AlbumDTO;
import com.bkb.metalmusicreviews.backend.entity.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("postgresAlbum")
public class AlbumDataAccess implements DataAccessAlbum {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AlbumDataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Album> getAlbumsByUsername(String username) {
        final String sql = "SELECT albums.album_id, albums.album_spotify_id, albums.album_name, albums.album_year, albums.band_id FROM albums JOIN users_albums ON albums.album_id = users_albums.album_id JOIN users ON users_albums.user_id = users.user_id WHERE username = ?";
        return jdbcTemplate.query(
                sql,
                new Object[]{
                        username
                },
                (resultSet,i) -> {
                    int albumId = resultSet.getInt("album_id");
                    String albumSpotifyId = resultSet.getString("album_spotify_id");
                    String albumName = resultSet.getString("album_name");
                    int albumYear = resultSet.getInt("album_year");
                    int bandId = resultSet.getInt("band_id");
                    return new Album(albumId, albumSpotifyId, albumName, albumYear, bandId);
                });
    }

    @Override
    public List<Album> getAlbumsByBandIdAndUsername(String username, int inputBandId) {
        final String sql = "SELECT albums.album_id, albums.album_spotify_id, albums.album_name, albums.album_year, albums.band_id FROM albums JOIN users_albums ON albums.album_id = users_albums.album_id AND albums.band_id = ? JOIN users ON users_albums.user_id = users.user_id WHERE username = ?";
        return jdbcTemplate.query(
                sql,
                new Object[]{
                        inputBandId,
                        username
                },
                (resultSet,i) -> {
                    int albumId = resultSet.getInt("album_id");
                    String albumSpotifyId = resultSet.getString("album_spotify_id");
                    String albumName = resultSet.getString("album_name");
                    int albumYear = resultSet.getInt("album_year");
                    int bandId = resultSet.getInt("band_id");
                    return new Album(albumId, albumSpotifyId, albumName, albumYear, bandId);
                });
    }

    @Override
    public int isAlbumExistBySpotifyId(String albumSpotifyId) {

        final String sql = "SELECT album_id FROM albums Where album_spotify_id = ?";
        List<Integer> result = new ArrayList<>();
        try {
            jdbcTemplate.query(
                    sql,
                    new Object[]{albumSpotifyId},
                    resultSet -> {
                        result.add(resultSet.getInt("album_id"));
                        return;
                    });
        }catch (IncorrectResultSizeDataAccessException e){
            System.out.println("error");
        }
        if(result.size() == 0){
            return -1;
        }
        return result.get(0);
    }

    @Override
    public int addAlbum(AlbumDTO albumDTO) {
        final String sql = "INSERT INTO albums(album_spotify_id, album_name, album_year, band_id) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                new Object[]{
                        albumDTO.getAlbumSpotifyId(),
                        albumDTO.getAlbumName(),
                        albumDTO.getAlbumYear(),
                        albumDTO.getBandId()
                });
    }

    @Override
    public int addAlbumForThisUser(int userId, int albumId) {
        final String sql = "INSERT INTO users_albums(user_id, album_id) VALUES(?, ?)";
        return jdbcTemplate.update(
                sql,
                new Object[]{
                        userId,
                        albumId
                });
    }

    @Override
    public Optional<Album> getAlbumById(int inputAlbumId) {
        final String sql = "SELECT album_id, album_spotify_id, album_name, album_year, band_id FROM albums Where album_id = ?";
        Album album = jdbcTemplate.queryForObject(
                sql,
                new Object[]{inputAlbumId},
                (resultSet, i) -> {
                    int albumId = resultSet.getInt("album_id");
                    String albumSpotifyId= resultSet.getString("album_spotify_id");
                    String albumName = resultSet.getString("album_name");
                    int albumYear = resultSet.getInt("album_year");
                    int bandId =  resultSet.getInt("band_id");
                    return new Album(albumId, albumSpotifyId, albumName, albumYear, bandId);
                });

        return Optional.ofNullable(album);
    }

    @Override
    public int deleteAlbumByIdAndUserId(int albumId, int userId) {
        final String sql = "DELETE FROM users_albums WHERE user_id = ? AND album_id = ?";
        jdbcTemplate.update(
                sql,
                new Object[]{userId, albumId});
        return 1;
    }

    @Override
    public int getAlbumCountByUsername(String username) {
        final String sql = "SELECT count(*) FROM users_albums WHERE user_id IN (SELECT user_id FROM users WHERE username = ?)";
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
