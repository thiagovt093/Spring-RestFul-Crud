package com.exemplo.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @JsonCreator
    public static UserRole fromString(String value){
        return valueOf(value.toUpperCase());
    }
}
