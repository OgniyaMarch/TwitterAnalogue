package com.example.demo.controller;


import com.example.demo.domen.response.Response;
import com.example.demo.service.TwitterAnalogueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("twitter-analogue")
public class Controller {

    private final TwitterAnalogueService twitterAnalogueService;

    @GetMapping("/hello")
    public String hello(){
        String hello = "Hello, Twitter analogue service! Version: 1.0.0";
        log.info(hello);
        return hello;
    }

    @PostMapping("/test")
    public ResponseEntity<Response> test(){
        log.info("START endpoint test");
        ResponseEntity<Response> response = twitterAnalogueService.test();
        log.info("END endpoint test, response: {}", response);
        return response;
    }

}
