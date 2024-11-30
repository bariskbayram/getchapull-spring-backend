package com.bkb.metalmusicreviews.backend.repository;

import com.bkb.metalmusicreviews.backend.entity.Band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpaRepoBand")
public interface BandRepository extends JpaRepository<Band, Integer> {

    @Query("select distinct b from Band b join b.albums a join a.userAlbums ua join ua.user u where u.username = :username")
    List<Band> findBandsByUsername(String username);

    Optional<Band> findBandByBandSpotifyId(String spotifyId);

    @Query(
            value = "select count(distinct bands.band_id) from bands inner join albums on bands.band_id = albums.band_band_id inner join user_album ON albums.album_id = user_album.album_album_id WHERE user_album.user_user_id IN (SELECT user_id FROM users WHERE username = :username)",
            nativeQuery = true
    )
    int getBandCountByUsername(String username);

}
