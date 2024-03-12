package com.example.demo.controller;

import com.example.demo.domain.response.Response;
import com.example.demo.service.ReactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("twitter-analogue/communication/reaction")
public class ReactionController {

    private final ReactionService reactionService;

    @GetMapping("/deleteLikePost/{postId}")
    public ResponseEntity<Response> deleteLikePost(@RequestHeader String accessToken, @PathVariable long postId){
        log.info("START endpoint deleteLikePost accessToken: {}, postId: {}", accessToken, postId);
        ResponseEntity<Response> response = reactionService.deleteLikePost(accessToken, postId);
        log.info("END endpoint deleteLikePost, response: {}", response);
        return response;
    }

    @GetMapping("/likePost/{postId}")
    public ResponseEntity<Response> likePost(@RequestHeader String accessToken, @PathVariable long postId){
        log.info("START endpoint likePost accessToken: {}, postId: {}", accessToken, postId);
        ResponseEntity<Response> response = reactionService.likePost(accessToken, postId);
        log.info("END endpoint likePost, response: {}", response);
        return response;
    }
}
