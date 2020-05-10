package com.example.atm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class RequestCustomException extends RuntimeException {
    public RequestCustomException(String message) {
        super(message);
    }

    public RequestCustomException(String message, Throwable cause) {
        super(message, cause);
    }
}