package com.example.demo.service;

import com.example.demo.domain.api.common.PostResp;

import java.util.List;

public interface CommonService {
    void postEnrichment(List<PostResp> posts);
}
