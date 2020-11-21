package com.bkb.metalmusicreviews.backend.service;

import com.bkb.metalmusicreviews.backend.dao.DataAccessReview;
import com.bkb.metalmusicreviews.backend.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewService {

    private final DataAccessReview dataAccessReview;

    @Autowired
    public ReviewService(@Qualifier("postgresReview") DataAccessReview dataAccessReview) {
        this.dataAccessReview = dataAccessReview;
    }

    public void addReview(Review review) {
        dataAccessReview.addReview(review);
    }

    public Optional<Review> getReviewByAlbumId(UUID albumId, String username) {
        return dataAccessReview.getReviewByAlbumId(albumId, username);
    }

    public void deteReviewByReviewId(Integer reviewId) {
        dataAccessReview.deleteReviewById(reviewId);
    }

    public List<Review> getReviewsForPosts(List<String> friend_list) {
        return dataAccessReview.getReviewsForPosts(friend_list);
    }
}
