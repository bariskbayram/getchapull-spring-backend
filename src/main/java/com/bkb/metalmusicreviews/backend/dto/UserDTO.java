package com.bkb.metalmusicreviews.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDTO {

    private int userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("fullname")
    private String fullname;

    @JsonProperty("bio_info")
    private String bioInfo;

    public UserDTO() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBioInfo() {
        return bioInfo;
    }

    public void setBioInfo(String bioInfo) {
        this.bioInfo = bioInfo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
