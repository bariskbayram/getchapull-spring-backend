package com.bkb.getchapull.backend.repository;

import com.bkb.getchapull.backend.entity.UserProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository("jpaRepoUserProfile")
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

    @Query("select u from UserProfile u inner join u.userList ul where ul.following.userId = :userId and ul.user.userId != :userId")
    List<UserProfile> getFollowersByUserId(int userId);

    @Query(
            value = "select u2.user_id, u2.username, u2.email, u2.password, u2.fullname, u2.user_created, u2.bio_info, u2.user_role from users u inner join user_following uf on u.user_id = uf.user_user_id and u.user_id = :userId inner join users u2 ON uf.following_user_id = u2.user_id WHERE uf.following_user_id != :userId",
            nativeQuery = true
    )
    List<UserProfile> getFollowingsByUserId(int userId);

    Optional<UserProfile> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void deleteUserProfileByUsername(String username);

    @Modifying
    @Query("update UserProfile u set u.fullname = :fullname, u.bioInfo = :bioInfo, u.password = :password where u.username = :username")
    void updateUserProfile(String fullname, String bioInfo, String password, String username);

    @Modifying
    @Transactional
    @Query(value = "insert into user_following(user_user_id, following_user_id) values (:userId, :followingId)", nativeQuery = true)
    void followSomeone(int userId, int followingId);

    @Modifying
    @Transactional
    @Query(value = "delete from user_following where user_user_id = :userId and following_user_id = :followingId", nativeQuery = true)
    void unfollowSomeone(int userId, int followingId);

    @Query(value = "SELECT username FROM user_following inner join users on users.user_id = user_following.following_user_id where user_following.user_user_id = :userId and users.username = :otherUsername", nativeQuery = true)
    String isFollowedByUser(Integer userId, String otherUsername);
}
