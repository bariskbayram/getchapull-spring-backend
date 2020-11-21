package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.model.Review;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DataAccessReview {
    List<Review> getAllReviews();
    void addReview(Review review);
    Optional<Review> getReviewById(Integer id);
    void deleteReviewById(Integer id);
    void updateReviewById(Integer id, Review review);
    Optional<Review> getReviewByAlbumId(UUID albumId, String username);
    List<Review> getReviewsForPosts(List<String> friend_list);
}
