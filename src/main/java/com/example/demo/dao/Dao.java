package com.example.demo.dao;


import com.example.demo.domen.dto.User;
import org.springframework.stereotype.Service;

@Service
public interface Dao {
    String getAccessToken(User user);

    boolean isExistsNickname(String nickname);

    void insertNewUser(User user);
}
