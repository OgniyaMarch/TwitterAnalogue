package com.example.demo.service.impl;

import com.example.demo.dao.CommonDao;
import com.example.demo.dao.ReactionDao;
import com.example.demo.domain.response.Response;
import com.example.demo.domain.response.SuccessResponse;
import com.example.demo.service.ReactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {
    private final CommonDao commonDao;
    private final ReactionDao reactionDao;

    @Override
    public ResponseEntity<Response> deleteLikePost(String accessToken, long postId) {
        long userId = commonDao.getUserIdByToken(accessToken);
        reactionDao.deleteLikePost(userId, postId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> likePost(String accessToken, long postId) {
        long userId = commonDao.getUserIdByToken(accessToken);
        reactionDao.likePost(userId, postId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }
}
