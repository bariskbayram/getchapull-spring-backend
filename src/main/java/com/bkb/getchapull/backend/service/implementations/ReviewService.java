package com.bkb.getchapull.backend.service.implementations;

import com.bkb.getchapull.backend.dto.PostDTO;
import com.bkb.getchapull.backend.dto.ReviewDTO;
import com.bkb.getchapull.backend.entity.Album;
import com.bkb.getchapull.backend.entity.Review;
import com.bkb.getchapull.backend.entity.UserProfile;
import com.bkb.getchapull.backend.repository.ReviewRepository;
import com.bkb.getchapull.backend.service.interfaces.ReviewServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
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
    public Optional<Review> getPostByAlbumIdAndUsername(Long albumId, String username) {
        return reviewRepository.getPostByAlbumIdAndUsername(albumId, username);
    }

    @Override
    public void deteReviewByReviewId(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public void updateReviewByReviewId(Long reviewId, ReviewDTO reviewDTO) {
        reviewRepository.updateReviewByReviewId(reviewId, reviewDTO.getReviewTitle(), reviewDTO.getReviewContent(), reviewDTO.getReviewPoint());
    }

    @Override
    public List<PostDTO> getPostsByUserId(Long userId) {
        List<Object[]> results = reviewRepository.getPostsByUserId(userId);

        return results.stream()
                .map(row -> new PostDTO(
                        (String) row[0],     // users.username
                        (Long) row[1],       // reviews.review_id
                        (String) row[2],     // reviews.review_title
                        (String) row[3],     // reviews.review_content
                        (Integer) row[4],    // reviews.review_point
                        ((Instant) row[5]).atOffset(ZoneOffset.UTC),  // reviews.posting_date TODO: not sure about using OffsetDateTime, better check Saul.Timezone da hatalı
                        (Long) row[8],       // albums.album_id
                        (String) row[9],     // albums.album_name
                        (Long) row[10],      // albums.band_band_id
                        (String) row[11]     // bands.band_name
                ))
                .collect(Collectors.toList());
    }
}
