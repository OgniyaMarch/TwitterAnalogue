package com.example.demo.domain.api.user.getMyPosts;


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
public class PostResp {
    private long postId;
    private String text;
    private String timeInsert;
    private List<TagResp> tags;
}
