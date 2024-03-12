package com.example.demo.domain.api.communication.getMyPublishersPosts;

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
public class GetMyPublishersPostsResp {
    private List<PostResp> posts;
}
