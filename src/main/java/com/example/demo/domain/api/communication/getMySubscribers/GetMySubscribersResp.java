package com.example.demo.domain.api.communication.getMySubscribers;

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
public class GetMySubscribersResp {
    private List<UserResp> subscribers;
}
