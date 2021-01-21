package com.bkb.metalmusicreviews.backend.service.implementations;

import com.bkb.metalmusicreviews.backend.dto.PostDTO;
import com.bkb.metalmusicreviews.backend.dto.ReviewDTO;
import com.bkb.metalmusicreviews.backend.entity.Album;
import com.bkb.metalmusicreviews.backend.entity.Review;
import com.bkb.metalmusicreviews.backend.entity.UserProfile;
import com.bkb.metalmusicreviews.backend.repository.ReviewRepository;
import com.bkb.metalmusicreviews.backend.service.interfaces.ReviewServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("jpaServiceReview")
public class ReviewService implements ReviewServiceInterface {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(@Qualifier("jpaRepoReview") ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getPostsByUserId(int userId) {
        return reviewRepository.getPostsByUserId(userId);
    }

    @Override
    public void addReview(ReviewDTO reviewDTO) {
        Review review = new Review(
                reviewDTO.getReviewTitle(),
                reviewDTO.getReviewContent(),
                reviewDTO.getReviewPoint()
        );

        review.setAlbum(new Album(reviewDTO.getAlbumId()));
        review.setUserProfile(new UserProfile(reviewDTO.getUserId()));

        reviewRepository.save(review);
    }

    @Override
    public Optional<Review> getPostByAlbumIdAndUsername(int albumId, String username) {
        return reviewRepository.getPostByAlbumIdAndUsername(albumId, username);
    }

    @Override
    public void deteReviewByReviewId(int reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public void updateReviewByReviewId(int reviewId, ReviewDTO reviewDTO) {
        reviewRepository.updateReviewByReviewId(reviewId, reviewDTO.getReviewTitle(), reviewDTO.getReviewContent(), reviewDTO.getReviewPoint());
    }
}
