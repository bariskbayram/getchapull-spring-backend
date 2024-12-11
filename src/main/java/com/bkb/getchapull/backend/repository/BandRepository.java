package com.bkb.getchapull.backend.repository;

import com.bkb.getchapull.backend.entity.Band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpaRepoBand")
public interface BandRepository extends JpaRepository<Band, Long> {

    @Query("SELECT r.album.band, MAX(r.createdAt) FROM Review r WHERE r.userProfile.username = :username GROUP BY r.album.band ORDER BY MAX(r.createdAt) DESC")
    List<Band> findBandsReviewedByUser(String username);

    Optional<Band> findBandBySpotifyId(String spotifyId);

    @Query("SELECT COUNT(DISTINCT r.album.band) FROM Review r WHERE r.userProfile.username = :username")
    int getReviewedBandCountByUser(String username);

}
