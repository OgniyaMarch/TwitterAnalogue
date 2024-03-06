package com.example.demo.domen.response.exception;


import com.example.demo.domen.constant.Code;
import com.example.demo.domen.response.error.Error;
import com.example.demo.domen.response.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class PostServiceErrorHandler {
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException (CommonException ex){
        log.error("CommonException: {}", ex.toString());
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder()
                        .code(ex.getCode())
                        .userMessage(ex.getUserMessage())
                        .techMessage(ex.getTechMessage())
                .build()).build(), ex.getHttpStatus());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestException (MissingRequestHeaderException ex){
        log.error("MissingRequestHeaderException: {}", ex.toString());
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder()
                        .code(Code.MISSING_REQUEST_HEADER)
                        .techMessage(ex.getMessage())
                .build()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedErrorException(Exception ex){
        log.error("Exception: {}", ex.toString());
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder()
                        .code(Code.INTERNAL_SERVER_ERROR)
                        .userMessage("Внутрішня помилка сервісу")
                .build()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
