package com.bkb.getchapull.backend.repository;

import com.bkb.getchapull.backend.entity.Band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpaRepoBand")
public interface BandRepository extends JpaRepository<Band, Long> {

    @Query("select distinct b from Band b join b.albums a join a.userAlbums ua join ua.user u where u.username = :username")
    List<Band> findBandsByUsername(String username);

    Optional<Band> findBandBySpotifyId(String spotifyId);

    @Query(
            value = "select count(distinct bands.id) from bands inner join albums on bands.id = albums.band_id inner join user_album ON albums.id = user_album.album_id WHERE user_album.user_id IN (SELECT id FROM users WHERE username = :username)",
            nativeQuery = true
    )
    int getBandCountByUsername(String username);

}
