package com.example.demo.service.impl;

import com.example.demo.dao.CommonDao;
import com.example.demo.domain.api.common.PostResp;
import com.example.demo.service.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {
    private final CommonDao commonDao;

    @Override
    public void postEnrichment(List<PostResp> posts) {
        for (PostResp postResp: posts){
            postResp.setTags(commonDao.getTagsByPostId(postResp.getPostId()));
            postResp.setCountLikes(commonDao.getCountLikes(postResp.getPostId()));
        }
    }
}
