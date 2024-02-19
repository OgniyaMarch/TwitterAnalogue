package com.example.demo.service.impl;


import com.example.demo.domen.response.error.Error;
import com.example.demo.domen.response.error.ErrorResponse;
import com.example.demo.domen.response.Response;
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
        /*return new ResponseEntity<>
                (SuccessResponse
                        .builder()
                        .data("SuccessResponse")
                        .build(),
                        HttpStatus.OK);
        */
        return new ResponseEntity<>
                (ErrorResponse
                        .builder()
                        .error(Error
                                .builder()
                                .code("VALIDATION_ERROR")
                                .message("Ошибка валидации")
                                .build())
                        .build(),
                        HttpStatus.BAD_REQUEST);

    }
   /* public ResponseEntity<Response> test() {
        return new ResponseEntity<>
                (ErrorResponse
                .builder()
                .error(Error
                        .builder()
                        .code("VALIDATION_ERROR")
                        .message("Ошибка валидации")
                        .build())
                .build(),
                HttpStatus.BAD_REQUEST);
    }*/
}
