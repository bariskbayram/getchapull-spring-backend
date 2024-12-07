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

    @Query(value = "select users.username, reviews.id, reviews.title, reviews.content, reviews.point, reviews.created_at, reviews.album_id, reviews.user_profile_id, albums.id, albums.name, albums.band_id, bands.name from reviews inner join albums on reviews.album_id = albums.id inner join bands on albums.band_id = bands.id inner join users on reviews.user_profile_id = users.id inner join follows on users.id = follows.followed_id WHERE follows.follower_id = :userId ORDER BY reviews.created_at DESC", nativeQuery = true)
    List<Object[]> getPostsByUserId(Long userId);

    @Query(value = "select reviews.id, reviews.title, reviews.content, reviews.point, reviews.created_at, reviews.album_id, reviews.user_profile_id, albums.name, bands.id, bands.name from reviews inner join albums on reviews.album_id = albums.id and albums.id = :albumId inner join bands on bands.id = albums.band_id inner join users ON reviews.user_profile_id = users.id WHERE username = :username", nativeQuery = true)
    Optional<Review> getPostByAlbumIdAndUsername(Long albumId, String username);

    @Modifying
    @Transactional
    @Query(value = "update reviews set title = :reviewTitle, content = :reviewContent, point = :reviewPoint where id = :reviewId", nativeQuery = true)
    Review updateReviewByReviewId(Long reviewId, String reviewTitle, String reviewContent, int reviewPoint);
}
