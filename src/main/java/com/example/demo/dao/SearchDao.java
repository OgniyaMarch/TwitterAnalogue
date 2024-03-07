package com.example.demo.dao;

import com.example.demo.domain.api.search.searchTags.TagResp;

import java.util.List;

public interface SearchDao {
    List<TagResp> searchTags(String partTag);
}
