package com.example.atm.service;

import com.example.atm.entity.ATM;
import com.example.atm.entity.BankAccount;

public interface ATMService {
    void addCashToATM(ATM atm, Long Dollar100, Long Dollar200, Long Dollar500);

    void getCashFromATM(ATM atm, BankAccount bankAccount, Long value);
}
