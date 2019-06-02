package com.benny.blog.utils.jwt;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JWTAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 1L;

    public JWTAuthenticationToken(Object principal) {
        super(principal, null, emptyList());
    }


    private static Collection<? extends GrantedAuthority> emptyList() {
        return null;
    }

}