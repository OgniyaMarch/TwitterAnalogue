package com.example.demo.service;

import com.example.demo.domain.api.common.PostResp;

import java.util.List;

public interface CommonService {
    void postEnrichment(List<PostResp> posts);
    void checkBlockByPostId(long userId, long postId);
    void checkBlockByUserId(long userId, long checkBlockUserId);
}
