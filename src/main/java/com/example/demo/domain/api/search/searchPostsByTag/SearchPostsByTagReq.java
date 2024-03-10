package com.example.demo.domain.api.search.searchPostsByTag;


import com.example.demo.domain.constant.Sort;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchPostsByTagReq {
    @DecimalMin(value = "1", message = "Значення tagId повинно бути більше нуля")
    private long tagId;

    @NotNull(message = "sort повинен бути заповнений")
    private Sort sort;
}
