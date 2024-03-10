package com.example.demo.service;

import com.example.demo.domain.api.search.searchPostsByTag.SearchPostsByTagReq;
import com.example.demo.domain.api.search.searchTags.SearchTagsReq;
import com.example.demo.domain.response.Response;
import org.springframework.http.ResponseEntity;

public interface SearchService {
    ResponseEntity<Response> searchPostsByTag(SearchPostsByTagReq req, String accessToken);
    ResponseEntity<Response> searchTags(SearchTagsReq req, String accessToken);
}
