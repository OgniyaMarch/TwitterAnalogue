package com.example.demo.service;

import com.example.demo.domen.api.RegistrationReq;
import com.example.demo.domen.response.Response;
import org.springframework.http.ResponseEntity;

public interface TwitterAnalogueService {
    ResponseEntity<Response> registration(RegistrationReq req);
}
