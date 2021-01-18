package com.bkb.metalmusicreviews.backend.service;

import com.bkb.metalmusicreviews.backend.dao.DataAccessReview;
import com.bkb.metalmusicreviews.backend.dto.PostDTO;
import com.bkb.metalmusicreviews.backend.dto.ReviewDTO;
import com.bkb.metalmusicreviews.backend.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final DataAccessReview dataAccessReview;

    @Autowired
    public ReviewService(@Qualifier("postgresReview") DataAccessReview dataAccessReview) {
        this.dataAccessReview = dataAccessReview;
    }

    public void addReview(ReviewDTO reviewDTO) {
        dataAccessReview.addReview(reviewDTO);
    }

    public Optional<PostDTO> getPostByAlbumIdAndUsername(int albumId, String username) {
        return dataAccessReview.getPostByAlbumIdAndUsername(albumId, username);
    }

    public void deteReviewByReviewId(Integer reviewId) {
        dataAccessReview.deleteReviewById(reviewId);
    }

    public void updateReviewByReviewId(int reviewId, ReviewDTO reviewDTO) {
        dataAccessReview.updateReviewById(reviewId, reviewDTO);
    }

    public List<PostDTO> getPostsByUserId(int userId) {
        return dataAccessReview.getPostsByUserId(userId);
    }
}
