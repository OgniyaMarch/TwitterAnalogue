package com.example.demo.service;

import com.example.demo.domen.api.LoginReq;
import com.example.demo.domen.api.PublicPostReq;
import com.example.demo.domen.api.RegistrationReq;
import com.example.demo.domen.response.Response;
import org.springframework.http.ResponseEntity;

public interface TwitterAnalogueService {
    ResponseEntity<Response> registration(RegistrationReq req);

    ResponseEntity<Response> login(LoginReq req);

    ResponseEntity<Response> publicPost(PublicPostReq req, String access_token);

    ResponseEntity<Response> getMyPosts(String accessToken);
}
