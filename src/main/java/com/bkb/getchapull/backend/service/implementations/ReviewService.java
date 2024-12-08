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
                reviewDTO.getTitle(),
                reviewDTO.getContent(),
                reviewDTO.getPoint()
        );

        review.setAlbum(new Album(reviewDTO.getAlbumId()));
        review.setUserProfile(new UserProfile(reviewDTO.getUserId()));

        reviewRepository.save(review);
    }

    @Override
    public PostDTO getPostByAlbumIdAndUsername(Long albumId, String username) {
        return reviewRepository.getPostByAlbumIdAndUsername(albumId, username)
                .map(review -> new PostDTO(
                        review.getId(),
                        username,
                        review.getTitle(),
                        review.getContent(),
                        review.getPoint(),
                        null,
                        review.getAlbum().getId(),
                        review.getAlbum().getName(),
                        review.getAlbum().getBand().getId(),
                        review.getAlbum().getBand().getName()
                )).orElse(null);
    }

    @Override
    public void deteReviewByReviewId(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public void updateReviewByReviewId(Long reviewId, ReviewDTO reviewDTO) {
        reviewRepository.updateReviewByReviewId(reviewId, reviewDTO.getTitle(), reviewDTO.getContent(), reviewDTO.getPoint());
    }

    @Override
    public List<PostDTO> getPostsByUserId(Long userId) {
        List<Object[]> results = reviewRepository.getPostsByUserId(userId);

        return results.stream()
                .map(row -> new PostDTO(
                        (Long) row[0],       // reviews.id
                        (String) row[1],     // users.username
                        (String) row[2],     // reviews.title
                        (String) row[3],     // reviews.content
                        (Integer) row[4],    // reviews.point
                        ((Instant) row[5]).atOffset(ZoneOffset.UTC),  // reviews.created_at TODO: not sure about using OffsetDateTime, better check Saul.Timezone da hatalı
                        (Long) row[6],       // albums.id
                        (String) row[7],     // albums.name
                        (Long) row[8],      // band.id
                        (String) row[9]     // bands.name
                ))
                .collect(Collectors.toList());
    }
}
