package com.example.demo.service.impl;

import com.example.demo.dao.CommonDao;
import com.example.demo.domain.api.common.PostResp;
import com.example.demo.domain.constant.Code;
import com.example.demo.domain.response.exception.CommonException;
import com.example.demo.service.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
            postResp.setCountLikes(commonDao.getCountLikesByPostId(postResp.getPostId()));
            postResp.setComments(commonDao.getCommentsByPostId(postResp.getPostId()));
        }
    }

    @Override
    public void checkBlockByPostId(long userId, long postId) {
        long checkBlockUserId = commonDao.getUserIdByPostId(postId);
        checkBlock(userId, checkBlockUserId);
    }

    @Override
    public void checkBlockByUserId(long userId, long checkBlockUserId) {
        checkBlock(userId, checkBlockUserId);
    }

    private void checkBlock(long userId, long checkBlockUserId){
        if (commonDao.isBlocked(userId, checkBlockUserId)){
            throw CommonException.builder()
                    .code(Code.BLOCKED)
                    .userMessage("Ви заблоковані цим користувачем")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
}
