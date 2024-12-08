package com.bkb.getchapull.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    @JsonProperty("id")
    private Long id;

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

    public UserDTO(Long id, String username, String email, String fullname, String bioInfo) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.bioInfo = bioInfo;
    }
}
