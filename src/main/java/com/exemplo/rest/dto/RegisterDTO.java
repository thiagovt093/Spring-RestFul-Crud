package com.exemplo.rest.dto;

import com.exemplo.rest.model.UserRole;

public record RegisterDTO (String email, String password, UserRole role, String name){
}
