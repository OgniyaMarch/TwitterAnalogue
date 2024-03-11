package com.example.demo.domain.api.search.searchPostsByPartWord;


import com.example.demo.domain.constant.RegExp;
import com.example.demo.domain.constant.Sort;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchPostsByPartWordReq {
    @NotBlank(message = "partWord повинен бути заповнений")
    @Pattern(regexp = RegExp.partWord, message = "Wrong partWord")
    private String partWord;

    @NotNull(message = "sort повинен бути заповнений")
    private Sort sort;
}
