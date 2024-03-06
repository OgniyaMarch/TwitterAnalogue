package com.example.demo.domain.api;


import com.example.demo.domain.constant.RegExp;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicPostReq {
    @NotBlank(message = "post должен быть заполнен")
    @Pattern(regexp = RegExp.post, message = "Wrong post")
    private String text;

    @Size(max = 5, message = "Количество тегов не должно превышать 5")
    private List<
            @NotBlank(message = "Tag is empty")
            @Pattern(regexp = RegExp.tag, message = "Wrong tag")
                    String> tags;
    
}
