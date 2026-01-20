package com.beyond.basic.b2_board.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

//Controller 어노테이션이 붙어있는 모든 클래스의 예외를 아래 클래스에서 안터셉팅(가로채기).
@RestControllerAdvice   //예외를 캐치하는 Controller
public class CommonExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illiega(IllegalArgumentException e) {
        e.printStackTrace();
        CommonErrorDto dto = CommonErrorDto.builder()
                .status_code(400).error_message(e.getMessage()).
                build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        CommonErrorDto dto = CommonErrorDto.builder()
                .status_code(400)
                .error_message(e.getFieldError().getDefaultMessage()).
                build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> NoSuchElement(NoSuchElementException e) {
        e.printStackTrace();
        CommonErrorDto dto = CommonErrorDto.builder()
                .status_code(404)
                .error_message(e.getMessage()).
                build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonErrorDto exception(Exception e) {
        e.printStackTrace();
        CommonErrorDto dto = CommonErrorDto.builder()
                .status_code(500)
                .error_message(e.getMessage()).
                build();
        return dto;
    }
}