package com.example.demo.dao;

import com.example.demo.domain.api.common.PostResp;
import com.example.demo.domain.api.common.UserResp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscriptionDao {
    List<UserResp> getMySubscribers(long userId);
    List<UserResp> getMyPublishers(long userId);
    void unsubscription(long subUserId, long pubUserId);
    void subscription(long subUserId, long pubUserId);
    List<PostResp> getMyPublishersPosts(long userId, int from, int limit);
}
