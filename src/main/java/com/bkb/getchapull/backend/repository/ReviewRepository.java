package com.bkb.getchapull.backend.repository;

import com.bkb.getchapull.backend.entity.Review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository("jpaRepoReview")
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT r.id AS id, u.username AS username, r.title AS title, r.content AS content, r.point AS point, r.created_at AS created_at, a.id AS album_id, a.name AS album_name, b.id AS band_id, b.name AS band_name, r.user_profile_id AS user_profile_id FROM reviews r INNER JOIN albums a on r.album_id = a.id and a.id = :albumId INNER JOIN bands b ON b.id = a.band_id INNER JOIN users u ON r.user_profile_id = u.id WHERE u.username = :username", nativeQuery = true)
    Optional<Review> getPostByAlbumIdAndUsername(Long albumId, String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE reviews SET title = :reviewTitle, content = :reviewContent, point = :reviewPoint WHERE id = :reviewId", nativeQuery = true)
    Review updateReviewByReviewId(Long reviewId, String reviewTitle, String reviewContent, int reviewPoint);

    @Query(value = "select reviews.id, users.username, reviews.title, reviews.content, reviews.point, reviews.created_at, albums.id, albums.name, albums.band_id, bands.name from reviews inner join albums on reviews.album_id = albums.id inner join bands on albums.band_id = bands.id inner join users on reviews.user_profile_id = users.id inner join follows on users.id = follows.followed_id WHERE follows.follower_id = :userId ORDER BY reviews.created_at DESC", nativeQuery = true)
    List<Object[]> getPostsByUserId(Long userId);
}
