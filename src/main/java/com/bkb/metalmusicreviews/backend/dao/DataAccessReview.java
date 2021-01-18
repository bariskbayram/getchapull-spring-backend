package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.dto.PostDTO;
import com.bkb.metalmusicreviews.backend.dto.ReviewDTO;

import java.util.List;
import java.util.Optional;

public interface DataAccessReview {
    void addReview(ReviewDTO reviewDTO);
    Optional<PostDTO> getPostByAlbumIdAndUsername(int albumId, String username);
    void deleteReviewById(int reviewId);
    void updateReviewById(int reviewId, ReviewDTO reviewDTO);
    List<PostDTO> getPostsByUserId(int userId);
}
