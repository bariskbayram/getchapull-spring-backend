package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgresReview")
public class ReviewDataAccess implements DataAccessReview {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReviewDataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Review> getAllReviews() {
        return null;
    }

    @Override
    public void addReview(Review review) {
        final String sql = "INSERT INTO review(REVIEW_ID, REVIEW_TITLE, REVIEW_CONTENT, REVIEW_POINT, ALBUM_ID, USERNAME) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                new Object[]{
                        review.getReviewId(),
                        review.getTitle(),
                        review.getContent(),
                        review.getPoint(),
                        review.getAlbumId(),
                        review.getUsername()
                });
    }

    @Override
    public Optional<Review> getReviewById(UUID id) {
        return Optional.empty();
    }

    @Override
    public void deleteReviewById(UUID id) {
        final String sql = "DELETE FROM review WHERE REVIEW_ID = ?";
        jdbcTemplate.update(
                sql,
                new Object[]{id});
    }

    @Override
    public void updateReviewById(UUID id, Review review) {

    }

    @Override
    public Optional<Review> getReviewByAlbumId(UUID albumId, String username) {
        final String sql = "SELECT * FROM review WHERE ALBUM_ID = ? AND USERNAME = ?";
        Review review = jdbcTemplate.queryForObject(
                sql,
                new Object[]{
                        albumId,
                        username
                },
                (resultSet, i) -> {
                    UUID id = UUID.fromString(resultSet.getString("REVIEW_ID"));
                    String title = resultSet.getString("REVIEW_TITLE");
                    String content = resultSet.getString("REVIEW_CONTENT");
                    String review_point = resultSet.getString("REVIEW_POINT");
                    return new Review(id, title, content, review_point, albumId, username);
                }
        );
        return Optional.ofNullable(review);
    }
}
