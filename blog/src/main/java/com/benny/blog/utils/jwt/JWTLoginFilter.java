package com.benny.blog.utils.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.benny.blog.utils.JsonHelper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    static final String TOKEN_PREFIX = "Bearer";

    static final String HEADER_STRING = "Authorization";

    private AuthenticationSuccessHandler successHandler;

    public JWTLoginFilter() {

    }

    public JWTLoginFilter(AuthenticationManager authManager) {
        setAuthenticationManager(authManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication auth) throws IOException, ServletException {

        TokenAuthenticationHandler tokenAuthenticationHandler = new TokenAuthenticationHandler();

        Object obj = auth.getPrincipal();

        if(obj != null) {
            UserDetails userDetails = (UserDetails)obj;
            String token = tokenAuthenticationHandler.generateToken(JsonHelper.fromObject(userDetails));
            response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + token);
        }

        if(successHandler != null) {
            successHandler.onAuthenticationSuccess(request, response, auth);
        }

    }

    public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }
}