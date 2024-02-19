package com.example.demo.service.impl;


import com.example.demo.domen.constant.Code;
import com.example.demo.domen.response.Response;
import com.example.demo.domen.response.exception.CommonException;
import com.example.demo.service.TwitterAnalogueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TwitterAnalogueServiceImpl  implements TwitterAnalogueService {

    @Override
    public ResponseEntity<Response> test(){

        throw CommonException
                .builder()
                .code(Code.TEST)
                .message("Test")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

    }

}
