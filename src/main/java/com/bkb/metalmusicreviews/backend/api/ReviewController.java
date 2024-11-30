package com.bkb.metalmusicreviews.backend.api;

import com.bkb.metalmusicreviews.backend.dto.PostDTO;
import com.bkb.metalmusicreviews.backend.dto.ReviewDTO;
import com.bkb.metalmusicreviews.backend.entity.Review;
import com.bkb.metalmusicreviews.backend.service.interfaces.ReviewServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin("*")
public class ReviewController {

    private ReviewServiceInterface reviewService;

    @Autowired
    public ReviewController(@Qualifier("jpaServiceReview") ReviewServiceInterface reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/upload_review")
    @PreAuthorize("hasAuthority('review:write')")
    public void addReview(@RequestPart("review_dto") ReviewDTO reviewDTO){
        reviewService.addReview(reviewDTO);
    }

    @GetMapping(path = "/get_post_by_album_id_and_username")
    @PreAuthorize("hasAuthority('review:read')")
    public Review getPostByAlbumIdAndUsername(
            @RequestParam(name = "album_id") int albumId,
            @RequestParam(name = "username") String username){
        return reviewService.getPostByAlbumIdAndUsername(albumId, username).orElse(null);
    }

    @DeleteMapping(path = "/delete_review_by_review_id")
    @PreAuthorize("hasAuthority('review:write')")
    public void deleteReviewByReviewId(@RequestParam(name = "review_id") int reviewId){
        reviewService.deteReviewByReviewId(reviewId);
    }

    @PutMapping("/update_review_by_review_id")
    @PreAuthorize("hasAuthority('review:write')")
    public void updateReviewByReviewId(@RequestBody ReviewDTO reviewDTO, @RequestParam(name = "review_id") int reviewId){
        reviewService.updateReviewByReviewId(reviewId, reviewDTO);
    }

    @PostMapping(path = "/get_all_post_by_user_id")
    @PreAuthorize("hasAuthority('review:read')")
    public List<PostDTO> getPostsByUserId(@RequestParam(name = "user_id") int userId){
        return reviewService.getPostsByUserId(userId);
    }

}
