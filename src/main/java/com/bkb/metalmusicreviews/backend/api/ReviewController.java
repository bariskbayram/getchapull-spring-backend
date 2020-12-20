package com.bkb.metalmusicreviews.backend.api;

import com.bkb.metalmusicreviews.backend.model.Review;
import com.bkb.metalmusicreviews.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin("*")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('review:write')")
    public void addReview(@RequestParam("review_title") String reviewTitle,
                          @RequestParam("review_content") String reviewContent,
                          @RequestParam("review_point") String reviewPoint,
                          @RequestParam("album_id") UUID albumId,
                          @RequestParam("album_name") String albumName,
                          @RequestParam("band_id") UUID bandId,
                          @RequestParam("band_name") String bandName,
                          @RequestParam("username") String username,
                          @RequestParam("date") String date){

        reviewService.addReview(
                new Review(
                        1,
                        reviewTitle,
                        reviewContent,
                        reviewPoint,
                        albumId,
                        albumName,
                        bandId,
                        bandName,
                        username,
                        date));

    }

    @GetMapping(path = "{albumId}")
    @PreAuthorize("hasAuthority('review:read')")
    public Review getReviewByAlbumId(
            @PathVariable("albumId") UUID albumId,
            @RequestParam(name = "username") String username){
        return reviewService.getReviewByAlbumId(albumId, username).orElse(null);
    }

    @DeleteMapping(path = "{reviewId}")
    @PreAuthorize("hasAuthority('review:write')")
    public void deleteReviewByReviewId(@PathVariable("reviewId") Integer reviewId){
        reviewService.deteReviewByReviewId(reviewId);
    }

    @PostMapping(path = "/get-for-posts")
    @PreAuthorize("hasAuthority('review:read')")
    public List<Review> getReviewsForPosts(@RequestBody List<String> friend_list){
        return reviewService.getReviewsForPosts(friend_list);
    }

}
