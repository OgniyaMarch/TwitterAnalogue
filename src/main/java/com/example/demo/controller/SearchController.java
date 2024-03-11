package com.example.demo.controller;


import com.example.demo.domain.api.search.searchPostsByPartWord.SearchPostsByPartWordReq;
import com.example.demo.domain.api.search.searchPostsByTag.SearchPostsByTagReq;
import com.example.demo.domain.api.search.searchTags.SearchTagsReq;
import com.example.demo.domain.response.Response;
import com.example.demo.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("twitter-analogue/search")
public class SearchController {
    private final SearchService searchService;

    @PostMapping("/searchTags")
    public ResponseEntity<Response> searchTags (@RequestHeader String accessToken, @RequestBody final SearchTagsReq req){
        log.info("START endpoint searchTags, accessToken: {}, request: {}", accessToken, req);
        ResponseEntity<Response> response = searchService.searchTags(req, accessToken);
        log.info("END endpoint searchTags, response: {}", response);
        return response;
    }

    @PostMapping("/searchPostsByTag")
    public ResponseEntity<Response> searchPostsByTag (@RequestHeader String accessToken, @RequestBody final SearchPostsByTagReq req){
        log.info("START endpoint searchPostsByTag, accessToken: {}, request: {}", accessToken, req);
        ResponseEntity<Response> response = searchService.searchPostsByTag(req, accessToken);
        log.info("END endpoint searchPostsByTag, response: {}", response);
        return response;
    }

    @PostMapping("/searchPostsByPartWord")
    public ResponseEntity<Response> searchPostsByPartWord(@RequestHeader String accessToken, @RequestBody final SearchPostsByPartWordReq req){
        log.info("START endpoint searchPostsByPartWord, accessToken: {}, request: {}", accessToken, req);
        ResponseEntity<Response> response = searchService.searchPostsByPartWord(req, accessToken);
        log.info("END endpoint searchPostByPartWord, response: {}", response);
        return response;
    }
}
