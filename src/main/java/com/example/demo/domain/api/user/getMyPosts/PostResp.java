package com.example.demo.domain.api.user.getMyPosts;


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
    private long id;
    private String text;
    private String timeInsert;
    private List<String> tags;
}
