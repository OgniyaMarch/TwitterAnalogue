package com.example.demo.service.impl;

import com.example.demo.dao.CommonDao;
import com.example.demo.dao.SearchDao;
import com.example.demo.domain.api.common.PostResp;
import com.example.demo.domain.api.search.searchPostsByPartWord.SearchPostsByPartWordReq;
import com.example.demo.domain.api.search.searchPostsByTag.SearchPostsByTagReq;
import com.example.demo.domain.api.search.searchPostsByTag.SearchPostsByTagResp;
import com.example.demo.domain.api.search.searchTags.SearchTagsReq;
import com.example.demo.domain.api.search.searchTags.SearchTagsResp;
import com.example.demo.domain.api.common.TagResp;
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
    private final CommonDao commonDao;

    @Override
    public ResponseEntity<Response> searchPostsByTag(SearchPostsByTagReq req, String accessToken) {
        commonDao.getUserIdByToken(accessToken);
        List<PostResp> posts = searchDao.searchPostsByTag(req);
        for (PostResp postResp : posts){
            List<TagResp> tags = commonDao.getTagsByPostId(postResp.getPostId());
            postResp.setTags(tags);
        }
        return new ResponseEntity<>(SuccessResponse.builder()
                .data(SearchPostsByTagResp.builder()
                        .posts(posts)
                        .build())
                .build(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> searchTags(SearchTagsReq req, String accessToken) {
        commonDao.getUserIdByToken(accessToken);

        List<TagResp> tagRespList = searchDao.searchTags(req.getPartTag());
        return new ResponseEntity<>(SuccessResponse.builder()
                .data(SearchTagsResp.builder()
                        .tags(tagRespList).build())
                .build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> searchPostsByPartWord(SearchPostsByPartWordReq req, String accessToken) {
        commonDao.getUserIdByToken(accessToken);

        List<PostResp> posts = searchDao.searchPostsByPartWord(req);
        for(PostResp postResp : posts){
            List<TagResp> tags = commonDao.getTagsByPostId(postResp.getPostId());
            postResp.setTags(tags);
        }
        return new ResponseEntity<>(SuccessResponse.builder()
                .data(SearchPostsByTagResp.builder()
                        .posts(posts)
                        .build())
                .build(), HttpStatus.OK);
    }
}
