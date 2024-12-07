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

    @Query("select u from UserProfile u inner join u.following ul where ul.followed.id = :userId and ul.follower.id != :userId")
    List<UserProfile> getFollowersByUserId(Long userId);

    @Query(
            value = "select u2.id, u2.username, u2.email, u2.password, u2.fullname, u2.created_at, u2.bio_info, u2.role from users u inner join follows f on u.id = f.follower_id and u.id = :userId inner join users u2 ON f.followed_id = u2.id WHERE f.followed_id != :userId",
            nativeQuery = true
    )
    List<UserProfile> getFollowingsByUserId(Long userId);

    Optional<UserProfile> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void deleteUserProfileByUsername(String username);

    @Modifying
    @Query("update UserProfile u set u.fullname = :fullname, u.bioInfo = :bioInfo, u.password = :password where u.username = :username")
    void updateUserProfile(String fullname, String bioInfo, String password, String username);

    @Modifying
    @Transactional
    @Query(value = "insert into follows(follower_id, followed_id) values (:userId, :followingId)", nativeQuery = true)
    void followSomeone(Long userId, Long followingId);

    @Modifying
    @Transactional
    @Query(value = "delete from follows where follower_id = :userId and followed_id = :followingId", nativeQuery = true)
    void unfollowSomeone(Long userId, Long followingId);

    @Query(value = "SELECT username FROM follows inner join users on users.id = follows.followed_id where follows.follower_id = :userId and users.username = :otherUsername", nativeQuery = true)
    String isFollowedByUser(Long userId, String otherUsername);
}
