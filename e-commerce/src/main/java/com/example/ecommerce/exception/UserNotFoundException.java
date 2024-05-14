package com.example.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
//exception dan extend etseydik handler yakalayamıyacaktı bu exception ı ve hata mesajını donduremeyecektik
    public UserNotFoundException(String message) {
        super(message);
    }
}
