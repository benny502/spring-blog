package com.benny.blog.configs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.benny.blog.entity.ResultModel;
import com.benny.blog.services.UserDetailsServiceImpl;
import com.benny.blog.utils.JsonHelper;
import com.benny.blog.utils.encrypt.EncryptUtils;
import com.benny.blog.utils.jwt.JWTAuthenticationFilter;
import com.benny.blog.utils.jwt.JWTLoginFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    UserDetailsService customUserDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/**").authenticated()
                .antMatchers(HttpMethod.POST, "/login").permitAll().anyRequest().permitAll().and()
                .addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public JWTLoginFilter loginFilter() throws Exception {
        JWTLoginFilter loginFilter = new JWTLoginFilter(authenticationManager());
        loginFilter.setSuccessHandler(new AuthenticationSuccessHandler() {

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {

            }
        });
        loginFilter.setAuthenticationFailureHandler((reqeust, response, exception) -> {
            response.setContentType("application/json");
            response.getWriter().write(JsonHelper.fromObject(new ResultModel(1, exception.getMessage())));
        });
        return loginFilter;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setHideUserNotFoundExceptions(false);
        return authenticationProvider;
    }

    private PasswordEncoder passwordEncoder() {
        return new PasswordEncoder(){
        
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return  encode(rawPassword) == encodedPassword;
            }
        
            @Override
            public String encode(CharSequence rawPassword) {
                return EncryptUtils.Encrypt(rawPassword.toString(), EncryptUtils.ENCRYPTTYPE.MD5.getName());
            }

        };
    }
}