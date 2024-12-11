package com.bkb.getchapull.backend.api;

import com.bkb.getchapull.backend.dto.PostDTO;
import com.bkb.getchapull.backend.dto.ReviewDTO;
import com.bkb.getchapull.backend.service.interfaces.ReviewServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> addReview(@RequestPart("review_dto") ReviewDTO reviewDTO){
        try {
            reviewService.addReview(reviewDTO);
            return ResponseEntity.ok("Review added successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(path = "/get_post_by_album_id_and_username")
    @PreAuthorize("hasAuthority('review:read')")
    public PostDTO getPostByAlbumIdAndUsername(
            @RequestParam(name = "album_id") Long albumId,
            @RequestParam(name = "username") String username){
        return reviewService.getPostByAlbumIdAndUsername(albumId, username);
    }

    @DeleteMapping(path = "/delete_review_by_review_id")
    @PreAuthorize("hasAuthority('review:write')")
    public void deleteReviewByReviewId(@RequestParam(name = "review_id") Long reviewId){
        reviewService.deteReviewByReviewId(reviewId);
    }

    @PutMapping("/update_review_by_review_id")
    @PreAuthorize("hasAuthority('review:write')")
    public void updateReviewByReviewId(@RequestBody ReviewDTO reviewDTO, @RequestParam(name = "review_id") Long reviewId){
        reviewService.updateReviewByReviewId(reviewId, reviewDTO);
    }

    @PostMapping(path = "/get_all_post_by_user_id")
    @PreAuthorize("hasAuthority('review:read')")
    public List<PostDTO> getPostsByUserId(@RequestParam(name = "user_id") Long userId){
        return reviewService.getPostsByUserId(userId);
    }

}
