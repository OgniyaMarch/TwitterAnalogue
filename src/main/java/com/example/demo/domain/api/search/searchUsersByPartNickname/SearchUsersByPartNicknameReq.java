package com.example.demo.domain.api.search.searchUsersByPartNickname;

import com.example.demo.domain.constant.RegExp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchUsersByPartNicknameReq {
    @NotBlank(message = "partNickname повинен бути заповнений")
    @Pattern(regexp = RegExp.partNickname, message = "Wrong partNickname")
    private String partNickname;
}
