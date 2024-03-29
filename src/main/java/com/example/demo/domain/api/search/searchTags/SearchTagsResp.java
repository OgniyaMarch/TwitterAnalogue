package com.example.demo.domain.api.search.searchTags;


import com.example.demo.domain.api.common.TagResp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchTagsResp {
    private List<TagResp> tags;
}
