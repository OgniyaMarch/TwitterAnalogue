package com.example.demo.domain.api.search.searchTags;


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
public class SearchTagsReq {
    @NotBlank(message = "partTag должен быть заполнен")
    @Pattern(regexp = RegExp.tag, message = "Wrong partTag")
    private String partTag;
}
