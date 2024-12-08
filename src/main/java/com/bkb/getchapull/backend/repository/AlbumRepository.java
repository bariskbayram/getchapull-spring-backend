package com.bkb.getchapull.backend.repository;

import com.bkb.getchapull.backend.entity.Album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "jpaRepoAlbum")
public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query("SELECT DISTINCT r.album, r.createdAt FROM Review r WHERE r.userProfile.username = :username ORDER BY r.createdAt DESC")
    List<Album> findAlbumsReviewedByUser(String username);

    @Query("SELECT DISTINCT r.album FROM Review r WHERE r.userProfile.username = :username AND r.album.band.id = :bandId")
    List<Album> findBandAlbumsReviewedByUser(String username, Long bandId);

    Optional<Album> findAlbumBySpotifyId(String spotifyId);

    @Query("SELECT COUNT(DISTINCT r.album) FROM Review r WHERE r.userProfile.username = :username")
    int getReviewedAlbumCountByUser(String username);

}
