package com.example.demo.service.impl;

import com.example.demo.dao.SearchDao;
import com.example.demo.dao.UserDao;
import com.example.demo.domain.api.search.searchTags.SearchTagsReq;
import com.example.demo.domain.api.search.searchTags.SearchTagsResp;
import com.example.demo.domain.api.search.searchTags.TagResp;
import com.example.demo.domain.response.Response;
import com.example.demo.domain.response.SuccessResponse;
import com.example.demo.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final SearchDao searchDao;
    private final UserDao userDao;

    @Override
    public ResponseEntity<Response> searchTags(SearchTagsReq req, String accessToken) {
        userDao.getUserIdByToken(accessToken);

        List<TagResp> tagRespList = searchDao.searchTags(req.getPartTag());
        return new ResponseEntity<>(SuccessResponse.builder()
                .data(SearchTagsResp.builder()
                        .tags(tagRespList).build())
                .build(), HttpStatus.OK);
    }
}
