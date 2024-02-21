package com.example.demo.service.impl;


import com.example.demo.dao.Dao;
import com.example.demo.domen.api.RegistrationReq;
import com.example.demo.domen.api.RegistrationResp;
import com.example.demo.domen.constant.Code;
import com.example.demo.domen.dto.User;
import com.example.demo.domen.response.Response;
import com.example.demo.domen.response.SuccessResponse;
import com.example.demo.domen.response.exception.CommonException;
import com.example.demo.service.TwitterAnalogueService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.validation.ValidationUtils;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TwitterAnalogueServiceImpl  implements TwitterAnalogueService {

//    private final ValidationUtils validationUtils;

    private final Dao dao;


    @Override
        public ResponseEntity<Response> registration(RegistrationReq req) {
//            validationUtils.validationRequest(req);
            if (dao.isExistsNickname(req.getNickname())){
                throw CommonException.builder().code(Code.NICKNAME_BUSY).message("This nickname is taken.").httpStatus(HttpStatus.BAD_REQUEST).build();
            }
            String accessToken = UUID.randomUUID().toString().replace("-","") +
                    System.currentTimeMillis();
            String encryptPassword = DigestUtils.md5DigestAsHex(req.getPassword().getBytes());

            dao.insertNewUser(User.builder().nickname(req.getNickname()).encryptPassword(encryptPassword).accessToken(accessToken).build());
            return new ResponseEntity<>(SuccessResponse.builder().data(RegistrationResp.builder().accessToken(accessToken).build()).build(), HttpStatus.OK);

        }

}
