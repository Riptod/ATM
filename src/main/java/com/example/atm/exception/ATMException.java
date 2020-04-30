package com.example.atm.exception;

public class ATMException extends RuntimeException {
    public ATMException(String message, Throwable cause) {
        super(message, cause);
    }

    public ATMException(String message) {
        super(message);
    }
}
