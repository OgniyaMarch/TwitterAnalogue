package com.example.demo.domain.api.user.registration;

import com.example.demo.domain.api.user.common.AuthorizationReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationReq {
    @Valid
    @NotNull(message = "authorization должен быть заполнен")
    private AuthorizationReq authorization;
}
