package com.bkb.metalmusicreviews.backend.dao;

import com.amazonaws.services.quicksight.model.PostgreSqlParameters;
import com.bkb.metalmusicreviews.backend.model.UserProfile;
import org.flywaydb.core.internal.database.postgresql.PostgreSQLParser;
import org.flywaydb.core.internal.database.postgresql.PostgreSQLType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
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
        final String sql = "SELECT USERNAME, PASSWORD, FULLNAME, USER_ROLE FROM user_details";
        return jdbcTemplate.query(
                sql,
                (resultSet,i) -> {
                    String username = resultSet.getString("USERNAME");
                    String password = resultSet.getString("PASSWORD");
                    String fullname = resultSet.getString("FULLNAME");
                    String role = resultSet.getString("USER_ROLE");
                    Set<? extends GrantedAuthority> grantedAuthorities = null;
                    if(role.equals("ADMIN")){
                        grantedAuthorities = ADMIN.getGrantedAuthorities();
                    }else{
                        grantedAuthorities = NORMAL.getGrantedAuthorities();
                    }
                    return new UserProfile(
                            username,
                            password,
                            fullname,
                            grantedAuthorities,
                            true,
                            true,
                            true,
                            true);
                });
    }

    @Override
    public void addUserProfile(UserProfile userProfile) {
        final String sql = "INSERT INTO user_details(USERNAME, PASSWORD, FULLNAME, USER_ROLE, IS_ACCOUNT_NON_EXPIRED, IS_ACCOUNT_NON_LOCKED, IS_CREDENTIALS_NON_EXPIRED, IS_ENABLED) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
                        "true"
                });
    }

    @Override
    public Optional<UserProfile> getUserProfileByUsername(String input_username) {
        final String sql = "SELECT USERNAME, PASSWORD, FULLNAME, USER_ROLE FROM user_details Where USERNAME = ?";
        UserProfile userProfile = jdbcTemplate.queryForObject(
                sql,
                new Object[]{input_username},
                (resultSet, i) -> {
                    String username = resultSet.getString("USERNAME");
                    String password = resultSet.getString("PASSWORD");
                    String fullname = resultSet.getString("FULLNAME");
                    String role = resultSet.getString("USER_ROLE");
                    Set<? extends GrantedAuthority> grantedAuthorities = null;
                    if(role.equals("ADMIN")){
                        grantedAuthorities = ADMIN.getGrantedAuthorities();
                    }else{
                        grantedAuthorities = NORMAL.getGrantedAuthorities();
                    }
                    return new UserProfile(
                            username,
                            password,
                            fullname,
                            grantedAuthorities,
                            true,
                            true,
                            true,
                            true);
                });

        return Optional.ofNullable(userProfile);
    }

    @Override
    public void deleteUserProfileByUsername(String username) {
        final String sql = "DELETE FROM user_details WHERE USERNAME = ?";
        jdbcTemplate.update(
                sql,
                new Object[]{username});
    }

    @Override
    public void updateUserProfileByUsername(String username, UserProfile userProfile) {

    }

    @Override
    public boolean usernameIsExist(String username) {
        final String sql = "SELECT USERNAME, FULLNAME FROM user_details Where USERNAME = ?";
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
        final String sql = "INSERT INTO user_details(USERNAME, PASSWORD, FULLNAME, USER_ROLE, IS_ACCOUNT_NON_EXPIRED, IS_ACCOUNT_NON_LOCKED, IS_CREDENTIALS_NON_EXPIRED, IS_ENABLED) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
}
