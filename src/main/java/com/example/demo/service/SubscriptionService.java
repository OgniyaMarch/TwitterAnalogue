package com.example.demo.service;

import com.example.demo.domain.api.communication.subscription.SubscriptionReq;
import com.example.demo.domain.api.communication.unsubscription.UnsubscriptionReq;
import com.example.demo.domain.response.Response;
import org.springframework.http.ResponseEntity;

public interface SubscriptionService {
    ResponseEntity<Response> getMySubscribers(String accessToken);
    ResponseEntity<Response> getMyPublishers(String accessToken);
    ResponseEntity<Response> unsubscription(UnsubscriptionReq req, String accessToken);
    ResponseEntity<Response> subscription(SubscriptionReq req, String accessToken);
    ResponseEntity<Response> getMyPublishersPosts(String accessToken, int from, int limit);
}
