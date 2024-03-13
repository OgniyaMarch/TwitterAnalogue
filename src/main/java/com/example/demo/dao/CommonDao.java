package com.example.demo.dao;


import com.example.demo.domain.api.common.CommentResp;
import com.example.demo.domain.api.common.TagResp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommonDao {
    List<TagResp> getTagsByPostId(long postId);

    long getUserIdByToken(String accessToken);

    void testSchedulerLock(String instanceName);

    long getCountLikesByPostId(long postId);

    List<CommentResp> getCommentsByPostId(long postId);

    long getUserIdByPostId(long postId);

    boolean isBlocked(long userId, long checkBlockUserId);
}
