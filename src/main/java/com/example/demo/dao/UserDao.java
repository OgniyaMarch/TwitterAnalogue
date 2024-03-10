package com.example.demo.dao;


import com.example.demo.domain.dto.User;
import com.example.demo.domain.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserDao {
    List<Post> getPostsByUserId(long userId);
    void addPostTag(long postId, String tag);

    void addTag(String tag);

    long addPost(long userId, String text);


    String getAccessToken(User user);

    boolean isExistsNickname(String nickname);

    void insertNewUser(User user);
}
