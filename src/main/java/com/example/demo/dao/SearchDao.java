package com.example.demo.dao;

import com.example.demo.domain.api.common.TagResp;
import com.example.demo.domain.api.common.PostResp;
import com.example.demo.domain.api.common.UserResp;
import com.example.demo.domain.api.search.searchPostsByPartWord.SearchPostsByPartWordReq;
import com.example.demo.domain.api.search.searchPostsByTag.SearchPostsByTagReq;

import java.util.List;

public interface SearchDao {
    List<TagResp> searchTags(String partTag);

    List<PostResp> searchPostsByTag(SearchPostsByTagReq req, long userId);

    List<PostResp> searchPostsByPartWord(SearchPostsByPartWordReq req, long userId);

    List<UserResp> searchUsersByPartNickname(String partNickname);
}
