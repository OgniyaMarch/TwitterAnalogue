package com.example.demo.service.impl;


import com.example.demo.domen.api.RegistrationReq;
import com.example.demo.domen.response.Response;
import com.example.demo.domen.response.SuccessResponse;
import com.example.demo.service.TwitterAnalogueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.util.ValidationUtils;

import javax.validation.Valid;

@Slf4j
@Service
@RequiredArgsConstructor
public class TwitterAnalogueServiceImpl  implements TwitterAnalogueService {

//    private final ValidationUtils validationUtils;

    @Override
    public ResponseEntity<Response> registration(RegistrationReq req) {
//        validationUtils.validationRequest(req);
        return new ResponseEntity<Response>((SuccessResponse.builder().data("Ok, ").build()), HttpStatus.OK);
    }

}
