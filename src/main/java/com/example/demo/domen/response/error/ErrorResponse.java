package com.example.demo.domen.response.error;


import com.example.demo.domen.response.Response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse implements Response {
    private Error error;
}
