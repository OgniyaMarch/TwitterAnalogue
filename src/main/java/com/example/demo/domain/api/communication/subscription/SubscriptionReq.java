package com.example.demo.domain.api.communication.subscription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionReq {
    @DecimalMin(value = "1", message = "Значення pubUserId повинно бути більше 0")
    private long pubUserId;
}
