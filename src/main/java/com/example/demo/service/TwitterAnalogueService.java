package com.example.demo.service;

import com.example.demo.domain.api.LoginReq;
import com.example.demo.domain.api.PublicPostReq;
import com.example.demo.domain.api.RegistrationReq;
import com.example.demo.domain.response.Response;
import org.springframework.http.ResponseEntity;

public interface TwitterAnalogueService {
    ResponseEntity<Response> registration(RegistrationReq req);

    ResponseEntity<Response> login(LoginReq req);

    ResponseEntity<Response> publicPost(PublicPostReq req, String access_token);

    ResponseEntity<Response> getMyPosts(String accessToken);
}
