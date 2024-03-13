package com.example.demo.dao;

import com.example.demo.domain.api.communication.comment.CommentPostReq;
import com.example.demo.domain.dto.WhoseComment;
import org.springframework.stereotype.Service;

@Service
public interface ReactionDao {
    void deleteLikePost(long userId, long postId);
    void likePost(long userId, long postId);
    void commentPost(long userId, CommentPostReq req);
    void deleteComment(long commentId);
    WhoseComment whoseComment(long commentId);
}
