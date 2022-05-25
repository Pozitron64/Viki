package com.example.Viki.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN, ROLE_WRITER, ROLE_MODERATOR;

    @Override
    public String getAuthority(){
        return name();
    }
}
