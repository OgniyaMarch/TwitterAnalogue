package com.example.demo.domen.api;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {

  @NotNull(message = "authorization должен быть заполнен")
  private Authorization authorization;
}
