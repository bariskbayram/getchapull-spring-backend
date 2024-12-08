package com.bkb.getchapull.backend.service.interfaces;

import com.bkb.getchapull.backend.dto.PostDTO;
import com.bkb.getchapull.backend.dto.ReviewDTO;

import java.util.List;

public interface ReviewServiceInterface {

    void addReview(ReviewDTO reviewDTO);

    PostDTO getPostByAlbumIdAndUsername(Long albumId, String username);

    void deteReviewByReviewId(Long reviewId);

    void updateReviewByReviewId(Long reviewId, ReviewDTO reviewDTO);

    List<PostDTO> getPostsByUserId(Long userId);

}
