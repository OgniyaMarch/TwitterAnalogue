package com.example.demo.domain.response.exception;


import com.example.demo.domain.constant.Code;
import com.example.demo.domain.response.error.Error;
import com.example.demo.domain.response.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
    Posted the errors that will be intercepted
    and what to do about them
 */

@Slf4j
@ControllerAdvice
public class TwitterAnalogueServiceErrorHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(CommonException ex){
        log.error("common error: {}", ex.toString());
        return new ResponseEntity<>(ErrorResponse
                .builder()
                .error(Error
                        .builder()
                        .code(ex.getCode())
                        .userMessage(ex.getUserMessage())
                        .techMessage(ex.getTechMessage())
                        .build())
                .build(), ex.getHttpStatus());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException ex){
        log.error("MissingRequestHeaderException: {}",  ex.toString());
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder()
                        .code(Code.MISSING_REQUEST_HEADER)
                        .techMessage(ex.getMessage())
                .build()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedErrorException(Exception ex){
        log.error("internal server error: {}", ex.toString());
        return new ResponseEntity<>(ErrorResponse
                .builder()
                .error(Error
                        .builder()
                        .code(Code.INTERNAL_SERVER_ERROR)
                        .userMessage("Внутренняя ошибка сервиса")
                        .build())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
















}
