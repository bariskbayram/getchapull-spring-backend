package com.bkb.getchapull.backend.repository;

import com.bkb.getchapull.backend.entity.Album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository(value = "jpaRepoAlbum")
public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query("select a from Album a join fetch a.userAlbums ua join fetch ua.user u where u.username = :username")
    List<Album> findAlbumsByUsername(String username);

    @Query("select a from Album a join fetch a.userAlbums ua join fetch ua.user u where u.username = :username and a.band.id = :bandId")
    List<Album> findAlbumsByUsernameAndBandId(String username, Long bandId);

    Optional<Album> findAlbumBySpotifyId(String spotifyId);

    @Modifying
    @Transactional
    @Query(value = "insert into user_album(user_id, album_id) values(:userId, :albumId)", nativeQuery = true)
    int insertUserAlbum(Long userId, Long albumId);

    @Modifying
    @Transactional
    @Query(value = "delete from user_album where user_id = :userId and album_id = :albumId", nativeQuery = true)
    int deleteUserAlbum(Long albumId, Long userId);

    @Query(value = "select count(*) from user_album WHERE user_id IN (SELECT id FROM users WHERE username = :username)", nativeQuery = true)
    int getAlbumCountByUsername(String username);

}
