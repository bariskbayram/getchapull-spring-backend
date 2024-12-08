package com.bkb.getchapull.backend.repository;

import com.bkb.getchapull.backend.entity.UserProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository("jpaRepoUserProfile")
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Query("SELECT f.follower FROM Follow f WHERE f.followed.id = :userId AND f.follower.id != :userId")
    List<UserProfile> findFollowersByUserId(Long userId);

    @Query("SELECT f.followed FROM Follow f WHERE f.follower.id = :userId AND f.followed.id != :userId")
    List<UserProfile> findFollowingsByUserId(Long userId);

    Optional<UserProfile> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void deleteUserProfileByUsername(String username);

    @Modifying
    @Query("UPDATE UserProfile u SET u.fullname = :fullname, u.bioInfo = :bioInfo WHERE u.username = :username")
    void updateUserProfile(String username, String fullname, String bioInfo);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO follows(follower_id, followed_id) VALUES (:followerId, :followedId)", nativeQuery = true)
    void followSomeone(Long followerId, Long followedId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM follows WHERE follower_id = :unfollowerId AND followed_id = :unfollowedId", nativeQuery = true)
    void unfollowSomeone(Long unfollowerId, Long unfollowedId);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Follow f WHERE f.follower.id = :userId AND f.followed.username = :otherUsername")
    boolean isFollowedByUser(Long userId, String otherUsername);
}
