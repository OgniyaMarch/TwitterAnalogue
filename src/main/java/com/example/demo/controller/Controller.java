package com.example.demo.controller;


import com.example.demo.domen.api.RegistrationReq;
import com.example.demo.domen.response.Response;
import com.example.demo.service.TwitterAnalogueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/registration")
    public ResponseEntity<Response> registration(@RequestBody final RegistrationReq req) {
        log.info("START endpoint registration, request: {}", req);
        ResponseEntity<Response> resp = twitterAnalogueService.registration(req);
        log.info("END endpoint registration, response: {}", resp);
        return resp;
    }

}
