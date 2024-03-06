package com.example.demo.service;

import com.example.demo.domain.api.user.login.LoginReq;
import com.example.demo.domain.api.user.publicPost.PublicPostReq;
import com.example.demo.domain.api.user.registration.RegistrationReq;
import com.example.demo.domain.response.Response;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<Response> registration(RegistrationReq req);

    ResponseEntity<Response> login(LoginReq req);

    ResponseEntity<Response> publicPost(PublicPostReq req, String access_token);

    ResponseEntity<Response> getMyPosts(String accessToken);
}
