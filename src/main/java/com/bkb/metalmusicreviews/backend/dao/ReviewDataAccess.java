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
        final String sql = "INSERT INTO review(REVIEW_TITLE, REVIEW_CONTENT, REVIEW_POINT, ALBUM_ID, ALBUM_NAME, BAND_ID, BAND_NAME, USERNAME, DATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                new Object[]{
                        review.getTitle(),
                        review.getContent(),
                        review.getPoint(),
                        review.getAlbumId(),
                        review.getAlbumName(),
                        review.getBandId(),
                        review.getBandName(),
                        review.getUsername(),
                        review.getDate()
                });
    }

    @Override
    public Optional<Review> getReviewById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void deleteReviewById(Integer id) {
        final String sql = "DELETE FROM review WHERE REVIEW_ID = ?";
        jdbcTemplate.update(
                sql,
                new Object[]{id});
    }

    @Override
    public void updateReviewById(Integer id, Review review) {

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
                    Integer id = resultSet.getInt("REVIEW_ID");
                    String title = resultSet.getString("REVIEW_TITLE");
                    String content = resultSet.getString("REVIEW_CONTENT");
                    String review_point = resultSet.getString("REVIEW_POINT");
                    String album_name = resultSet.getString("ALBUM_NAME");
                    UUID band_id = UUID.fromString(resultSet.getString("BAND_ID"));
                    String band_name = resultSet.getString("BAND_NAME");
                    String date = resultSet.getString("DATE");
                    return new Review(id, title, content, review_point, albumId, album_name, band_id, band_name, username, date);
                }
        );
        return Optional.ofNullable(review);
    }

    @Override
    public List<Review> getReviewsForPosts(List<String> friend_list) {
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM review WHERE ");
        int count = 0;
        for(String friend: friend_list){
            String temp = String.format("'%s'", friend);
            if(count == 0){
                stringBuilder.insert(stringBuilder.length(), "USERNAME = " + temp);
            }else{
                stringBuilder.insert(stringBuilder.length(), " OR USERNAME = " + temp);
            }
            count++;
        }
        stringBuilder.insert(stringBuilder.length(), " ORDER BY review_id DESC LIMIT 10");
        final String sql = stringBuilder.toString();
        return jdbcTemplate.query(
                sql,
                (resultSet,i) -> {
                    Integer id = resultSet.getInt("REVIEW_ID");
                    String title = resultSet.getString("REVIEW_TITLE");
                    String content = resultSet.getString("REVIEW_CONTENT");
                    String review_point = resultSet.getString("REVIEW_POINT");
                    UUID albumId = UUID.fromString(resultSet.getString("ALBUM_ID"));
                    String album_name = resultSet.getString("ALBUM_NAME");
                    UUID bandId = UUID.fromString(resultSet.getString("BAND_ID"));
                    String band_name = resultSet.getString("BAND_NAME");
                    String username = resultSet.getString("USERNAME");
                    String date = resultSet.getString("DATE");
                    return new Review(id, title, content, review_point, albumId, album_name, bandId, band_name, username, date);
                });
    }
}
