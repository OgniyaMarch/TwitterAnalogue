package com.example.demo.config;

import com.example.demo.filter.AuthorizationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilter(){
        final var filterBean = new FilterRegistrationBean<>(new AuthorizationFilter());
        filterBean.addUrlPatterns(
                "/twitter-analogue/search/*",
                "/twitter-analogue/communication/*",
                "/twitter-analogue/user/getMyPosts",
                "/twitter-analogue/user/publicPost"
        );
        return filterBean;
    }
}
