package com.example.demo.controller;


import com.example.demo.domain.api.user.login.LoginReq;
import com.example.demo.domain.api.user.publicPost.PublicPostReq;
import com.example.demo.domain.api.user.registration.RegistrationReq;
import com.example.demo.domain.response.Response;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("twitter-analogue/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/hello")
    public String hello(){
        String hello = "Hello, Twitter analogue service! Version: 1.0.0";
        log.info(hello);
        return hello;
    }


    @PostMapping("/registration")
    public ResponseEntity<Response> registration(@RequestBody final RegistrationReq req) {
        log.info("START endpoint registration, request: {}", req);
        ResponseEntity<Response> resp = userService.registration(req);
        log.info("END endpoint registration, response: {}", resp);
        return resp;
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody final LoginReq req) {
        log.info("START endpoint login, request: {}", req);
        ResponseEntity<Response> resp = userService.login(req);
        log.info("END endpoint login, response: {}", resp);
        return resp;
    }

    @PostMapping("/publicPost")
    public ResponseEntity<Response> publicPost(@RequestHeader final String accessToken, @RequestBody final PublicPostReq req){
        log.info("START endpoint publicPost, accessToken:{}, request: {}", accessToken, req);
        ResponseEntity<Response> response = userService.publicPost(req, accessToken);
        log.info("END endpoint publicPost, response: {}", response);
        return response;
    }

    @GetMapping("/getMyPosts")
    public ResponseEntity<Response> getMyPosts(@RequestHeader final String accessToken){
        log.info("START endpoint getMyPosts, accessToken: {}", accessToken);
        ResponseEntity<Response> response = userService.getMyPosts(accessToken);
        log.info("END endpoint getMyPosts, response: {}", response);
        return response;
    }
}
