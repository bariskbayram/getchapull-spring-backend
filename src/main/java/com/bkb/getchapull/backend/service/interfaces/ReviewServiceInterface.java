package com.bkb.getchapull.backend.service.interfaces;

import com.bkb.getchapull.backend.dto.PostDTO;
import com.bkb.getchapull.backend.dto.ReviewDTO;
import com.bkb.getchapull.backend.entity.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewServiceInterface {

    void addReview(ReviewDTO reviewDTO);

    Optional<Review> getPostByAlbumIdAndUsername(Long albumId, String username);

    void deteReviewByReviewId(Long reviewId);

    void updateReviewByReviewId(Long reviewId, ReviewDTO reviewDTO);

    List<PostDTO> getPostsByUserId(Long userId);

}
