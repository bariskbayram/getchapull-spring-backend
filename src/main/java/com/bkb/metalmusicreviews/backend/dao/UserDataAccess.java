package com.bkb.metalmusicreviews.backend.dao;

import com.bkb.metalmusicreviews.backend.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.SQLException;
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
    public List<UserProfile> getAllUserProfiles() {
        final String sql = "SELECT USERNAME, PASSWORD, FULLNAME, USER_ROLE, FRIENDS FROM user_profile";
        return jdbcTemplate.query(
                sql,
                (resultSet,i) -> {
                    String username = resultSet.getString("USERNAME");
                    String password = resultSet.getString("PASSWORD");
                    String fullname = resultSet.getString("FULLNAME");
                    String role = resultSet.getString("USER_ROLE");
                    Array friends = resultSet.getArray("FRIENDS");
                    Set<? extends GrantedAuthority> grantedAuthorities = null;
                    if(role.equals("ADMIN")){
                        grantedAuthorities = ADMIN.getGrantedAuthorities();
                    }else{
                        grantedAuthorities = NORMAL.getGrantedAuthorities();
                    }
                    UserProfile u = new UserProfile(
                            username,
                            password,
                            fullname,
                            grantedAuthorities,
                            true,
                            true,
                            true,
                            true);
                    u.setObjectFriend(friends);
                    return u;
                });
    }

    @Override
    public void addUserProfile(UserProfile userProfile) {
        final String sql = "INSERT INTO user_profile(USERNAME, PASSWORD, FULLNAME, USER_ROLE, IS_ACCOUNT_NON_EXPIRED, IS_ACCOUNT_NON_LOCKED, IS_CREDENTIALS_NON_EXPIRED, IS_ENABLED, FRIENDS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, string_to_array(?,','))";
        jdbcTemplate.update(
                sql,
                new Object[]{
                        userProfile.getUsername(),
                        passwordEncoder.encode(userProfile.getPassword()),
                        userProfile.getFullName(),
                       "NORMAL",
                        "true",
                        "true",
                        "true",
                        "true",
                        userProfile.getUsername()
                });
    }

    @Override
    public Optional<UserProfile> getUserProfileByUsername(String input_username) {
        final String sql = "SELECT USERNAME, PASSWORD, FULLNAME, USER_ROLE, FRIENDS FROM user_profile Where USERNAME = ?";
        UserProfile userProfile = jdbcTemplate.queryForObject(
                sql,
                new Object[]{input_username},
                (resultSet, i) -> {
                    String username = resultSet.getString("USERNAME");
                    String password = resultSet.getString("PASSWORD");
                    String fullname = resultSet.getString("FULLNAME");
                    String role = resultSet.getString("USER_ROLE");
                    Array friends = resultSet.getArray("FRIENDS");
                    Set<? extends GrantedAuthority> grantedAuthorities = null;
                    if(role.equals("ADMIN")){
                        grantedAuthorities = ADMIN.getGrantedAuthorities();
                    }else{
                        grantedAuthorities = NORMAL.getGrantedAuthorities();
                    }
                    UserProfile u = new UserProfile(
                            username,
                            password,
                            fullname,
                            grantedAuthorities,
                            true,
                            true,
                            true,
                            true);
                    u.setObjectFriend(friends);
                    return u;
                });

        return Optional.ofNullable(userProfile);
    }

    @Override
    public void deleteUserProfileByUsername(String username) {
        final String sql = "DELETE FROM user_profile WHERE USERNAME = ?";
        jdbcTemplate.update(
                sql,
                new Object[]{username});
    }

    @Override
    public void updateUserProfileByUsername(String username, UserProfile userProfile) {
        final String sql = "UPDATE user_profile SET FULLNAME = ?, PASSWORD = ? WHERE USERNAME = ?";
        jdbcTemplate.update(
                sql,
                new Object[]{
                        userProfile.getFullName(),
                        passwordEncoder.encode(userProfile.getPassword()),
                        username
                }
        );
    }

    @Override
    public boolean usernameIsExist(String username) {
        final String sql = "SELECT USERNAME, FULLNAME FROM user_profile Where USERNAME = ?";
        List<String> result = new ArrayList<>();
        try {
            jdbcTemplate.query(sql, new Object[]{username}, resultSet -> {
                result.add(resultSet.getString("USERNAME"));
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
    public void addUserProfileForAdmin(UserProfile userProfile) {
        final String sql = "INSERT INTO user_profile(USERNAME, PASSWORD, FULLNAME, USER_ROLE, IS_ACCOUNT_NON_EXPIRED, IS_ACCOUNT_NON_LOCKED, IS_CREDENTIALS_NON_EXPIRED, IS_ENABLED) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                new Object[]{
                        userProfile.getUsername(),
                        passwordEncoder.encode(userProfile.getPassword()),
                        userProfile.getFullName(),
                        "ADMIN",
                        "true",
                        "true",
                        "true",
                        "true"
                });
    }

    @Override
    public void addFriend(String username, String friendUsername) {
        final String sqlPut = "UPDATE user_profile SET FRIENDS = array_append(FRIENDS, ?::text) WHERE USERNAME = ?";
        jdbcTemplate.update(
                sqlPut,
                new Object[]{
                        friendUsername,
                        username
                }
        );
    }
}
