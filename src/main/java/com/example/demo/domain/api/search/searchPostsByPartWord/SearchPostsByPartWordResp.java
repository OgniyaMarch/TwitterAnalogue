package com.example.demo.domain.api.search.searchPostsByPartWord;


import com.example.demo.domain.api.common.PostResp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchPostsByPartWordResp {
    private List<PostResp> posts;
}
