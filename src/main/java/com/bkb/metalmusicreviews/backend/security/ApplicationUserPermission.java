package com.bkb.metalmusicreviews.backend.security;

public enum ApplicationUserPermission {

    REVIEW_READ("review:read"),
    REVIEW_WRITE("review:write"),
    MEMBER_READ("member:read"),
    MEMBER_WRITE("member:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
