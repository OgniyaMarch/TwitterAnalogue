package com.example.demo.dao;

import org.springframework.stereotype.Service;

@Service
public interface ReactionDao {
    void deleteLikePost(long userId, long postId);
    void likePost(long userId, long postId);
}
