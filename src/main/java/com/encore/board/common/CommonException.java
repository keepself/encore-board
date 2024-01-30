package com.encore.board.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
//ControllerAdvice ExcepttionHandler를 통해 예외처리 예외ㅣ처리의 곹=ㅇ통화 로직 작성

@ControllerAdvice
@Slf4j
public class CommonException {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> entityNotFoundHandler(EntityNotFoundException e) {
        log.error("Handler EntityNotFoundException message : " + e.getMessage());
        return this.errResponseMessage(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> IllegalArgumentHandler(IllegalArgumentException e) {
        log.error("Handler IllegalArgumentException message : " + e.getMessage());
        return this.errResponseMessage(HttpStatus.BAD_REQUEST, e.getMessage());


    }


        private ResponseEntity<Map<String, Object>> errResponseMessage (HttpStatus status, String error){
            Map<String, Object> body = new HashMap<>();
            body.put("status", Integer.toString(status.value()));
            body.put("error message", error);
            return new ResponseEntity<>(body, status);
        }
    }

