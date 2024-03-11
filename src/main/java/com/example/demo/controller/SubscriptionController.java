package com.example.demo.controller;


import com.example.demo.domain.api.communication.subscription.SubscriptionReq;
import com.example.demo.domain.api.communication.unsubscription.UnsubscriptionReq;
import com.example.demo.domain.response.Response;
import com.example.demo.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("twitter-analogue/communication/subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping("/getMySubscribers")
    public ResponseEntity<Response> getMySubscribers(@RequestHeader String accessToken){
        log.info("START endpoint getMySubscribers accessToken: {}", accessToken);
        ResponseEntity<Response> response = subscriptionService.getMySubscribers(accessToken);
        log.info("END endpoint getMySubscribers, response: {}", response);
        return response;
    }

    @GetMapping("/getMyPublishers")
    public ResponseEntity<Response> getMyPublishers(@RequestHeader String accessToken){
        log.info("START endpoint getMyPublishers accessToken: {}", accessToken);
        ResponseEntity<Response> response = subscriptionService.getMyPublishers(accessToken);
        log.info("END endpoint getMyPublishers, response: {}", response);
        return response;
    }

    @PostMapping("/unsubscription")
    public ResponseEntity<Response> unsubscription(@RequestHeader String accessToken, @RequestBody final UnsubscriptionReq req){
        log.info("START endpoint unsubscription accessToken: {}, request: {}", accessToken, req);
        ResponseEntity<Response> response = subscriptionService.unsubscription(req, accessToken);
        log.info("END endpoint unsubscription, response: {}", response);
        return response;
    }

    @PostMapping("/subscription")
    public ResponseEntity<Response> subscription(@RequestHeader String accessToken, @RequestBody final SubscriptionReq req){
        log.info("START endpoint subscription accessToken: {}, request: {}", accessToken, req);
        ResponseEntity<Response> response = subscriptionService.subscription(req, accessToken);
        log.info("END endpoint subscription, response: {}", response);
        return response;
    }
}
