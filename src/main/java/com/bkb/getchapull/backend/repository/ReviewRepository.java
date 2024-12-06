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

    @Query(value = "select users.username, reviews.review_id, reviews.review_title, reviews.review_content, reviews.review_point, reviews.posting_date, reviews.album_album_id, reviews.user_profile_user_id, albums.album_id, albums.album_name, albums.band_band_id, bands.band_name from reviews inner join albums on reviews.album_album_id = albums.album_id inner join bands on albums.band_band_id = bands.band_id inner join users on reviews.user_profile_user_id = users.user_id inner join user_following on users.user_id = user_following.following_user_id WHERE user_following.user_user_id = :userId ORDER BY reviews.posting_date DESC", nativeQuery = true)
    List<Object[]> getPostsByUserId(Long userId);

    @Query(value = "select reviews.review_id, reviews.review_title, reviews.review_content, reviews.review_point, reviews.posting_date, reviews.album_album_id, reviews.user_profile_user_id, albums.album_name, bands.band_id, bands.band_name from reviews inner join albums on reviews.album_album_id = albums.album_id and albums.album_id = :albumId inner join bands on bands.band_id = albums.band_band_id inner join users ON reviews.user_profile_user_id = users.user_id WHERE username = :username", nativeQuery = true)
    Optional<Review> getPostByAlbumIdAndUsername(Long albumId, String username);

    @Modifying
    @Transactional
    @Query(value = "update reviews set review_title = :reviewTitle, review_content = :reviewContent, review_point = :reviewPoint where review_id = :reviewId", nativeQuery = true)
    Review updateReviewByReviewId(Long reviewId, String reviewTitle, String reviewContent, int reviewPoint);
}
