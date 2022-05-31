package com.example.Viki.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum TypeArticle implements GrantedAuthority {
    TYPE_MATH, TYPE_GAMES, TYPE_DEFAULT;

    @Override
    public String getAuthority(){
        return name();
    }
}
