package com.example.demo.service.impl;

import com.example.demo.dao.CommonDao;
import com.example.demo.dao.SubscriptionDao;
import com.example.demo.domain.api.common.CommonPostResp;
import com.example.demo.domain.api.common.PostResp;
import com.example.demo.domain.api.communication.getMyPublishers.GetMyPublishersResp;
import com.example.demo.domain.api.communication.getMySubscribers.GetMySubscribersResp;
import com.example.demo.domain.api.communication.subscription.SubscriptionReq;
import com.example.demo.domain.api.communication.unsubscription.UnsubscriptionReq;
import com.example.demo.domain.constant.Code;
import com.example.demo.domain.response.Response;
import com.example.demo.domain.response.SuccessResponse;
import com.example.demo.domain.response.exception.CommonException;
import com.example.demo.service.CommonService;
import com.example.demo.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionDao subscriptionDao;
    private final CommonDao commonDao;
    private final CommonService commonService;

    @Override
    public ResponseEntity<Response> getMySubscribers(String accessToken) {
        long userId = commonDao.getUserIdByToken(accessToken);
        log.info("userId: {}", userId);

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(GetMySubscribersResp.builder()
                        .subscribers(subscriptionDao.getMySubscribers(userId))
                        .build())
                .build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> getMyPublishers(String accessToken) {
        long userId = commonDao.getUserIdByToken(accessToken);
        log.info("userId: {}", userId);

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(GetMyPublishersResp.builder()
                        .publishers(subscriptionDao.getMyPublishers(userId))
                        .build())
                .build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> unsubscription(UnsubscriptionReq req, String accessToken) {
        long subUserId = commonDao.getUserIdByToken(accessToken);

        long pubUserId = req.getPubUserId();
        log.info("subUserId: {}, pubUserId: {}", subUserId, pubUserId);

        subscriptionDao.unsubscription(subUserId, pubUserId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> subscription(SubscriptionReq req, String accessToken) {
        long subUserId = commonDao.getUserIdByToken(accessToken);

        long pubUserId = req.getPubUserId();
        log.info("subUserId: {}, subscriptionUserId: {}",subUserId, pubUserId);

        if (subUserId == pubUserId){
            throw CommonException.builder().code(Code.SUBSCRIPTION_LOGIC_ERROR)
                    .userMessage("Ви не може підписатись на себе").httpStatus(BAD_REQUEST).build();
        }

        commonService.checkBlockByPostId(subUserId, pubUserId);
        subscriptionDao.subscription(subUserId, pubUserId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> getMyPublishersPosts(String accessToken, int from, int limit) {
        long userId = commonDao.getUserIdByToken(accessToken);
        log.info("userId: {}", userId);

        List<PostResp> posts = subscriptionDao.getMyPublishersPosts(userId, from, limit);
        commonService.postEnrichment(posts);

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(CommonPostResp.builder()
                        .posts(posts)
                        .build())
                .build(), HttpStatus.OK);
    }
}