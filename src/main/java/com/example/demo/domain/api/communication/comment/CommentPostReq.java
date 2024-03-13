package com.example.demo.domain.api.communication.comment;

import com.example.demo.domain.constant.RegExp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentPostReq {
    @DecimalMin(value = "1", message = "Значення postId повинно бути більше 0")
    private long postId;

    @NotBlank(message = "text повинен бути заповнений")
    @Pattern(regexp = RegExp.post, message = "Wrong text")
    private String text;
}
