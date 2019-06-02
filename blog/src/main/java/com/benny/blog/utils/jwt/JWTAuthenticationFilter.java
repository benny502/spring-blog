package com.benny.blog.utils.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

public class JWTAuthenticationFilter extends GenericFilterBean {

    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
                HttpServletRequest req = (HttpServletRequest)request;
                String token = req.getHeader(HEADER_STRING);
                if( !StringUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
                    TokenAuthenticationHandler tokenAuthenticationHandler = new TokenAuthenticationHandler();
                    String subject = tokenAuthenticationHandler.getSubjectFromToken(token.replace(TOKEN_PREFIX, ""));
                    if(!StringUtils.isEmpty(subject)) {
                        SecurityContextHolder.getContext().setAuthentication(new JWTAuthenticationToken(subject));
                    }
                }
                chain.doFilter(request, response);
     }
    
}

