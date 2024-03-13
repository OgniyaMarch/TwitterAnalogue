package com.example.demo.service;

import com.example.demo.domain.api.communication.comment.CommentPostReq;
import com.example.demo.domain.response.Response;
import org.springframework.http.ResponseEntity;

public interface ReactionService {
    ResponseEntity<Response> deleteCommentPost(String accessToken, long commentId);
    ResponseEntity<Response> commentPost(String accessToken, CommentPostReq req);
    ResponseEntity<Response> deleteLikePost(String accessToken, long postId);
    ResponseEntity<Response> likePost(String accessToken, long postId);
}
