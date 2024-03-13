package com.example.demo.domain.api.communication.getBlockUsers;

import com.example.demo.domain.api.common.UserResp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBlockUsersResp {
    private List<UserResp> blockUsers;
}
