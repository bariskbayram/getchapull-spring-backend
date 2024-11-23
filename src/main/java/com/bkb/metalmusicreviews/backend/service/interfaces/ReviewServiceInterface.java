package com.bkb.metalmusicreviews.backend.service.interfaces;

import com.bkb.metalmusicreviews.backend.dto.PostDTO;
import com.bkb.metalmusicreviews.backend.dto.ReviewDTO;
import com.bkb.metalmusicreviews.backend.entity.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewServiceInterface {

    void addReview(ReviewDTO reviewDTO);

    Optional<Review> getPostByAlbumIdAndUsername(int albumId, String username);

    void deteReviewByReviewId(int reviewId);

    void updateReviewByReviewId(int reviewId, ReviewDTO reviewDTO);

    List<PostDTO> getPostsByUserId(int userId);

}
