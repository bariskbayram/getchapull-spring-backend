package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.dto.UserDTO;
import com.bkb.metalmusicreviews.backend.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.bkb.metalmusicreviews.backend.security.ApplicationUserRole.ADMIN;
import static com.bkb.metalmusicreviews.backend.security.ApplicationUserRole.NORMAL;

@Repository("postgresUser")
public class UserDataAccess implements DataAccessUserProfile{

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDataAccess(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserProfile> getAllUsers() {
        final String sql = "SELECT * FROM users";
        return jdbcTemplate.query(
                sql,
                (resultSet,i) -> {
                    int userId = resultSet.getInt("user_id");
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    String fullname = resultSet.getString("fullname");
                    String bioInfo = resultSet.getString("bio_info");
                    String userCreated = resultSet.getString("user_created");
                    String userRole = resultSet.getString("user_role");
                    Set<? extends GrantedAuthority> grantedAuthorities = null;
                    if(userRole.equals("ADMIN")){
                        grantedAuthorities = ADMIN.getGrantedAuthorities();
                    }else{
                        grantedAuthorities = NORMAL.getGrantedAuthorities();
                    }
                    UserProfile u = new UserProfile(
                            userId,
                            username,
                            email,
                            password,
                            fullname,
                            bioInfo,
                            userCreated,
                            grantedAuthorities,
                            true,
                            true,
                            true,
                            true);
                    return u;
                });
    }

    @Override
    public List<UserProfile> getFollowers(int inputUserId) {
        final String sql = "SELECT * FROM users u INNER JOIN users_following uf ON u.user_id = uf.user_id WHERE following_id = ?";
        return jdbcTemplate.query(
                sql,
                new Object[]{inputUserId},
                (resultSet,i) -> {
                    int userId = resultSet.getInt("user_id");
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    String fullname = resultSet.getString("fullname");
                    String bioInfo = resultSet.getString("bio_info");
                    String userCreated = resultSet.getString("user_created");
                    String userRole = resultSet.getString("user_role");
                    Set<? extends GrantedAuthority> grantedAuthorities = null;
                    if(userRole.equals("ADMIN")){
                        grantedAuthorities = ADMIN.getGrantedAuthorities();
                    }else{
                        grantedAuthorities = NORMAL.getGrantedAuthorities();
                    }
                    UserProfile u = new UserProfile(
                            userId,
                            username,
                            email,
                            password,
                            fullname,
                            bioInfo,
                            userCreated,
                            grantedAuthorities,
                            true,
                            true,
                            true,
                            true);
                    return u;
                });
    }

    @Override
    public List<UserProfile> getFollowings(int inputUserId) {
        final String sql = "SELECT u2.user_id, u2.username, u2.email, u2.password, u2.fullname, u2.user_created, u2.bio_info, u2.user_role FROM users u INNER JOIN users_following uf ON u.user_id = uf.user_id AND u.user_id = ? INNER JOIN users u2 ON uf.following_id = u2.user_id";
        return jdbcTemplate.query(
                sql,
                new Object[]{inputUserId},
                (resultSet,i) -> {
                    int userId = resultSet.getInt("user_id");
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    String fullname = resultSet.getString("fullname");
                    String bioInfo = resultSet.getString("bio_info");
                    String userCreated = resultSet.getString("user_created");
                    String userRole = resultSet.getString("user_role");
                    Set<? extends GrantedAuthority> grantedAuthorities = null;
                    if(userRole.equals("ADMIN")){
                        grantedAuthorities = ADMIN.getGrantedAuthorities();
                    }else{
                        grantedAuthorities = NORMAL.getGrantedAuthorities();
                    }
                    UserProfile u = new UserProfile(
                            userId,
                            username,
                            email,
                            password,
                            fullname,
                            bioInfo,
                            userCreated,
                            grantedAuthorities,
                            true,
                            true,
                            true,
                            true);
                    return u;
                });
    }

    @Override
    public List<UserProfile> getUserSuggestion(int inputUserId) {
        final String sql = "SELECT u.user_id, u.username, u.email, u.password, u.fullname, u.user_created, u.bio_info, u.user_role FROM users u WHERE  NOT EXISTS (SELECT 1 FROM users_following uf WHERE  uf.following_id = u.user_id AND uf.user_id = ?) AND NOT EXISTS (SELECT 1 FROM users u2 WHERE u2.user_id = u.user_id AND u2.user_id = ?) LIMIT 5";
        return jdbcTemplate.query(
                sql,
                new Object[]{inputUserId, inputUserId},
                (resultSet,i) -> {
                    int userId = resultSet.getInt("user_id");
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    String fullname = resultSet.getString("fullname");
                    String bioInfo = resultSet.getString("bio_info");
                    String userCreated = resultSet.getString("user_created");
                    String userRole = resultSet.getString("user_role");
                    Set<? extends GrantedAuthority> grantedAuthorities = null;
                    if(userRole.equals("ADMIN")){
                        grantedAuthorities = ADMIN.getGrantedAuthorities();
                    }else{
                        grantedAuthorities = NORMAL.getGrantedAuthorities();
                    }
                    UserProfile u = new UserProfile(
                            userId,
                            username,
                            email,
                            password,
                            fullname,
                            bioInfo,
                            userCreated,
                            grantedAuthorities,
                            true,
                            true,
                            true,
                            true);
                    return u;
                });
    }

    @Override
    public Optional<UserProfile> loadUserByUsername(String input_username) {
        final String sql = "SELECT * FROM users Where username = ?";
        UserProfile userProfile = jdbcTemplate.queryForObject(
                sql,
                new Object[]{input_username},
                (resultSet, i) -> {
                    int userId = resultSet.getInt("user_id");
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    String fullname = resultSet.getString("fullname");
                    String bioInfo = resultSet.getString("bio_info");
                    String userCreated = resultSet.getString("user_created");
                    String userRole = resultSet.getString("user_role");
                    Set<? extends GrantedAuthority> grantedAuthorities = null;
                    if(userRole.equals("ADMIN")){
                        grantedAuthorities = ADMIN.getGrantedAuthorities();
                    }else{
                        grantedAuthorities = NORMAL.getGrantedAuthorities();
                    }
                    UserProfile u = new UserProfile(
                            userId,
                            username,
                            email,
                            password,
                            fullname,
                            bioInfo,
                            userCreated,
                            grantedAuthorities,
                            true,
                            true,
                            true,
                            true);
                    return u;
                });

        return Optional.ofNullable(userProfile);
    }

    @Override
    public void addUserProfile(UserDTO userDTO) {
        final String sql = "INSERT INTO users(username, email, password, fullname, bio_info, user_role) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                new Object[]{
                        userDTO.getUsername(),
                        userDTO.getEmail(),
                        passwordEncoder.encode(userDTO.getPassword()),
                        userDTO.getFullname(),
                        userDTO.getBioInfo(),
                        "NORMAL"
                });
        final String getSql = "SELECT last_value FROM users_user_id_seq";
        Integer userId = jdbcTemplate.queryForObject(
                getSql,
                (result, i) -> {
                    return result.getInt("last_value");
                }
        );
        followSomeone(userId, userId);
    }

    @Override
    public void addUserProfileForAdmin(UserDTO userDTO) {
        final String sql = "INSERT INTO users(username, email, password, fullname, bio_info, user_role) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                new Object[]{
                        userDTO.getUsername(),
                        userDTO.getEmail(),
                        passwordEncoder.encode(userDTO.getPassword()),
                        userDTO.getFullname(),
                        userDTO.getBioInfo(),
                        "ADMIN"
                });
    }

    @Override
    public boolean isUsernameExist(String username) {
        final String sql = "SELECT username FROM users Where username = ?";
        List<String> result = new ArrayList<>();
        try {
            jdbcTemplate.query(sql, new Object[]{username}, resultSet -> {
                result.add(resultSet.getString("username"));
                return;
            });
        }catch (IncorrectResultSizeDataAccessException e){
            System.out.println("error");
        }
        if(result.size() == 0){
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmailExist(String email) {
        final String sql = "SELECT email FROM users Where email = ?";
        List<String> result = new ArrayList<>();
        try {
            jdbcTemplate.query(sql, new Object[]{email}, resultSet -> {
                result.add(resultSet.getString("email"));
                return;
            });
        }catch (IncorrectResultSizeDataAccessException e){
            System.out.println("error");
        }
        if(result.size() == 0){
            return false;
        }
        return true;
    }

    @Override
    public void deleteUserProfileByUsername(String username) {
        final String sql = "DELETE FROM users WHERE username = ?";
        jdbcTemplate.update(
                sql,
                new Object[]{username});
    }

    @Override
    public void updateUserProfileByUsername(UserDTO userDTO) {
        final String sql = "UPDATE users SET fullname = ?, bio_info = ?, password = ? WHERE username = ?";
        jdbcTemplate.update(
                sql,
                new Object[]{
                        userDTO.getFullname(),
                        userDTO.getBioInfo(),
                        passwordEncoder.encode(userDTO.getPassword()),
                        userDTO.getUsername()
                }
        );
    }

    @Override
    public void followSomeone(int userId, int followingId) {
        final String sqlPut = "INSERT INTO users_following(user_id, following_id) VALUES (?,?)";
        jdbcTemplate.update(
                sqlPut,
                new Object[]{
                        userId,
                        followingId
                }
        );
    }

    @Override
    public void unfollowSomeone(int userId, int unfollowingId) {
        final String sqlPut = "DELETE FROM users_following WHERE user_id = ? AND following_id = ?";
        jdbcTemplate.update(
                sqlPut,
                new Object[]{
                        userId,
                        unfollowingId
                }
        );
    }

    @Override
    public boolean isYourFriend(int userId, String otherUsername) {
        final String sqlFind = "SELECT username FROM users_following INNER JOIN users ON users.user_id = users_following.following_id WHERE users_following.user_id = ? AND users.username = ?";
        List<String> result = new ArrayList<>();
        jdbcTemplate.query(sqlFind, new Object[]{userId, otherUsername}, resultSet -> {
            result.add(resultSet.getString("username"));
            return;
        });
        if(result.size() == 1){
            return true;
        }
        return false;
    }
}
