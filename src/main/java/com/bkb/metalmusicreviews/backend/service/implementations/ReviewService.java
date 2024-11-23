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

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("jpaServiceReview")
public class ReviewService implements ReviewServiceInterface {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(@Qualifier("jpaRepoReview") ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
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

    @Override
    public List<PostDTO> getPostsByUserId(int userId) {
        List<Object[]> results = reviewRepository.getPostsByUserId(userId);

        return results.stream()
                .map(row -> new PostDTO(
                        (String) row[0],     // users.username
                        (Integer) row[1],    // reviews.review_id
                        (String) row[2],     // reviews.review_title
                        (String) row[3],     // reviews.review_content
                        (Integer) row[4],    // reviews.review_point
                        (Timestamp) row[5],  // reviews.posting_date TODO: not sure about using Timestamp, better check Saul
                        (Integer) row[8],    // albums.album_id
                        (String) row[9],     // albums.album_name
                        (Integer) row[10],   // albums.band_band_id
                        (String) row[11]     // bands.band_name
                ))
                .collect(Collectors.toList());
    }
}
