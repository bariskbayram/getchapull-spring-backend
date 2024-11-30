package com.bkb.metalmusicreviews.backend.repository;

import com.bkb.metalmusicreviews.backend.entity.Album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository(value = "jpaRepoAlbum")
public interface AlbumRepository extends JpaRepository<Album, Integer> {

    @Query("select a from Album a join fetch a.userAlbums ua join fetch ua.user u where u.username = :username")
    List<Album> findAlbumsByUsername(String username);

    @Query("select a from Album a join fetch a.userAlbums ua join fetch ua.user u where u.username = :username and a.band.bandId = :bandId")
    List<Album> findAlbumsByUsernameAndBandId(String username, int bandId);

    Optional<Album> findAlbumByAlbumSpotifyId(String spotifyId);

    @Modifying
    @Transactional
    @Query(value = "insert into user_album(album_album_id, user_user_id) values(:albumId, :userId)", nativeQuery = true)
    int insertUserAlbum(int userId, int albumId);

    @Modifying
    @Transactional
    @Query(value = "delete from user_album where user_user_id = :userId and album_album_id = :albumId", nativeQuery = true)
    int deleteUserAlbum(int albumId, int userId);

    @Query(value = "select count(*) from user_album WHERE user_user_id IN (SELECT user_id FROM users WHERE username = :username)", nativeQuery = true)
    int getAlbumCountByUsername(String username);

}
