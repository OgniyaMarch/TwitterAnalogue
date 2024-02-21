package com.example.demo.util;


import com.example.demo.domen.constant.Code;
import com.example.demo.domen.response.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationUtils {

    private Validator validator;

    //TODO переделать валидацию

    public <T> void validationRequest(T req){
        if(req != null){
            Set<ConstraintViolation<T>> result = validator.validate(req);
            if(!result.isEmpty()){
                String resultValidations = result.stream()
                        .map(ConstraintViolation::getMessage)
                        .reduce((s1, s2) -> s1 + ". " + s2)
                        .orElse("");
                log.error("Переданный json в запросе не валиден, ошибка валидации", resultValidations);
                throw CommonException.builder().code(Code.REQUEST_VALIDATION_ERROR).message(resultValidations).httpStatus(HttpStatus.BAD_REQUEST).build();

            }
        }
    }

}
