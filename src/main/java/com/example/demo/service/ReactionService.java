package com.example.demo.service;

import com.example.demo.domain.response.Response;
import org.springframework.http.ResponseEntity;

public interface ReactionService {
    ResponseEntity<Response> deleteLikePost(String accessToken, long postId);
    ResponseEntity<Response> likePost(String accessToken, long postId);
}
