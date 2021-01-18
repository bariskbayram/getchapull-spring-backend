package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.dto.PostDTO;
import com.bkb.metalmusicreviews.backend.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("postgresReview")
public class ReviewDataAccess implements DataAccessReview {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReviewDataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addReview(ReviewDTO reviewDTO) {
        final String sql = "INSERT INTO reviews(review_title, review_content, review_point, album_id, user_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                new Object[]{
                        reviewDTO.getReviewTitle(),
                        reviewDTO.getReviewContent(),
                        reviewDTO.getReviewPoint(),
                        reviewDTO.getAlbumId(),
                        reviewDTO.getUserId()
                });
    }

    @Override
    public Optional<PostDTO> getPostByAlbumIdAndUsername(int inputAlbumId, String username) {
        final String sql = "SELECT  reviews.review_id, reviews.review_title, reviews.review_content, reviews.review_point, reviews.posting_date, reviews.album_id, albums.album_name, bands.band_id, bands.band_name FROM reviews INNER JOIN albums ON reviews.album_id = albums.album_id AND albums.album_id = ? INNER JOIN bands ON bands.band_id = albums.band_id INNER JOIN users ON reviews.user_id = users.user_id WHERE username = ?";
        PostDTO postDto = jdbcTemplate.queryForObject(
                sql,
                new Object[]{
                        inputAlbumId,
                        username
                },
                (resultSet, i) -> {
                    int reviewId = resultSet.getInt("review_id");
                    String reviewTitle = resultSet.getString("review_title");
                    String reviewContent = resultSet.getString("review_content");
                    int reviewPoint = resultSet.getInt("review_point");
                    String reviewPostingDate = resultSet.getString("posting_date");
                    int albumId = resultSet.getInt("album_id");
                    String albumName = resultSet.getString("album_name");
                    int bandId = resultSet.getInt("band_id");
                    String bandName = resultSet.getString("band_name");
                    return new PostDTO(username, reviewId, reviewTitle, reviewContent, reviewPoint, reviewPostingDate, albumId, albumName, bandId, bandName);
                }
        );
        return Optional.ofNullable(postDto);
    }

    @Override
    public void deleteReviewById(int reviewId) {
        final String sql = "DELETE FROM reviews WHERE review_id = ?";
        jdbcTemplate.update(
                sql,
                new Object[]{reviewId});
    }

    @Override
    public void updateReviewById(int id, ReviewDTO reviewDTO) {
        final String sql = "UPDATE reviews SET review_title = ?, review_content = ?, review_point = ? WHERE review_id = ?";
        jdbcTemplate.update(
                sql,
                new Object[]{
                        reviewDTO.getReviewTitle(),
                        reviewDTO.getReviewContent(),
                        reviewDTO.getReviewPoint()
                }
        );
    }

    @Override
    public List<PostDTO> getPostsByUserId(int userId) {
        final String sql = "SELECT users.username, reviews.review_id, reviews.review_title, reviews.review_content, reviews.review_point, reviews.posting_date, albums.album_id, albums.album_name, albums.band_id, bands.band_name FROM reviews INNER JOIN albums ON reviews.album_id = albums.album_id INNER JOIN bands ON albums.band_id = bands.band_id INNER JOIN users ON reviews.user_id = users.user_id INNER JOIN users_following ON users.user_id = users_following.following_id WHERE users_following.user_id = ? ORDER BY reviews.posting_date DESC";
        return jdbcTemplate.query(
                sql,
                new Object[]{
                        userId
                },
                (resultSet,i) -> {
                    String postUsername = resultSet.getString("username");
                    int reviewId = resultSet.getInt("review_id");
                    String reviewTitle = resultSet.getString("review_title");
                    String reviewContent = resultSet.getString("review_content");
                    int reviewPoint = resultSet.getInt("review_point");
                    String postingDate = resultSet.getString("posting_date");
                    int albumId = resultSet.getInt("album_id");
                    String albumName = resultSet.getString("album_name");
                    int bandId = resultSet.getInt("band_id");
                    String bandName = resultSet.getString("band_name");
                    return new PostDTO(postUsername, reviewId, reviewTitle, reviewContent, reviewPoint,  postingDate,  albumId, albumName, bandId, bandName);
                });
    }
}
