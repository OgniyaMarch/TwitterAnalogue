package com.example.demo.filter;


import com.example.demo.domain.constant.Code;
import com.example.demo.domain.response.error.Error;
import com.example.demo.domain.response.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        String accessToken = request.getHeader("AccessToken");
        if(Strings.isEmpty(accessToken)){
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .error(Error.builder()
                            .code(Code.AUTHORIZATION_ERROR)
                            .userMessage("Ошибка авторизации, выброшенная из фильтра")
                            .build())
                    .build();
            log.info("Отсутствует header AccessToken. ErrorResponse: {}", errorResponse);
            response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
            response.setStatus(BAD_REQUEST.value());
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
