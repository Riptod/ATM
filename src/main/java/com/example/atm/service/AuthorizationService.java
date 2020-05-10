package com.example.atm.service;

public interface AuthorizationService {
    boolean checkAccount(String userName, Long accountNumber);
}
