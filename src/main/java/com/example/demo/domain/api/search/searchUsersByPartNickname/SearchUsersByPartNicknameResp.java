package com.example.demo.domain.api.search.searchUsersByPartNickname;

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
public class SearchUsersByPartNicknameResp {
    private List<PostResp> posts;
}
