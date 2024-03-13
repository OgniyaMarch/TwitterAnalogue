package com.example.demo.controller;

import com.example.demo.domain.api.communication.comment.CommentPostReq;
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

    @DeleteMapping("/deleteLikePost/{postId}")
    public ResponseEntity<Response> deleteLikePost(@RequestHeader String accessToken, @PathVariable long postId){
        log.info("START endpoint deleteLikePost accessToken: {}, postId: {}", accessToken, postId);
        ResponseEntity<Response> response = reactionService.deleteLikePost(accessToken, postId);
        log.info("END endpoint deleteLikePost, response: {}", response);
        return response;
    }

    @PostMapping("/likePost/{postId}")
    public ResponseEntity<Response> likePost(@RequestHeader String accessToken, @PathVariable long postId){
        log.info("START endpoint likePost accessToken: {}, postId: {}", accessToken, postId);
        ResponseEntity<Response> response = reactionService.likePost(accessToken, postId);
        log.info("END endpoint likePost, response: {}", response);
        return response;
    }

    @PostMapping("/commentPhrase")
    public ResponseEntity<Response> commentPhrase(@RequestHeader String accessToken, @RequestBody final CommentPostReq req){
        log.info("START endpoint commentPost accessToken: {}, req: {}", accessToken, req);
        ResponseEntity<Response> response = reactionService.commentPost(accessToken, req);
        log.info("END endpoint commentPost, response: {}", response);
        return response;
    }

    @DeleteMapping("/deleteCommentPost/{commentId}")
    public ResponseEntity<Response> deleteCommentPost(@RequestHeader String accessToken, @PathVariable long commentId){
        log.info("START endpoint deleteCommentPost accessToken: {}, commentId: {}", accessToken, commentId);
        ResponseEntity<Response> response = reactionService.deleteCommentPost(accessToken, commentId);
        log.info("END endpoint deleteCommentPost, response: {}", response);
        return response;
    }

    @DeleteMapping("/unblockUser/{blockUserId}")
    public ResponseEntity<Response> unblockUser(@RequestHeader String accessToken, @PathVariable long blockUserId){
        log.info("START endpoint unblockUser accessToken: {}, commentId: {}", accessToken, blockUserId);
        ResponseEntity<Response> response = reactionService.unblockUser(accessToken, blockUserId);
        log.info("END endpoint unblockUser, response: {}", response);
        return response;
    }

    @GetMapping("/getBlockUsers")
    public ResponseEntity<Response> getBlockUsers(@RequestHeader String accessToken){
        log.info("START endpoint getBlockUsers accessToken: {}", accessToken);
        ResponseEntity<Response> response = reactionService.getBlockUsers(accessToken);
        log.info("END endpoint getBlockUsers, response: {}", response);
        return response;
    }

    @PostMapping("/blockUser/{blockUserId}")
    public ResponseEntity<Response> blockUser(@RequestHeader String accessToken, @PathVariable long blockUserId){
        log.info("START endpoint blockUser accessToken: {}, commentId: {}", accessToken, blockUserId);
        ResponseEntity<Response> response = reactionService.blockUser(accessToken, blockUserId);
        log.info("END endpoint blockUser, response: {}", response);
        return response;
    }
}
