package com.example.demo.dao;


import com.example.demo.domen.dto.User;
import org.springframework.stereotype.Service;

@Service
public interface Dao {
    void addPostTag(long postId, String tag);

    void addTag(String tag);

    long addPost(long userId, String text);

    long getIdByToken(String token);

    String getAccessToken(User user);

    boolean isExistsNickname(String nickname);

    void insertNewUser(User user);
}
