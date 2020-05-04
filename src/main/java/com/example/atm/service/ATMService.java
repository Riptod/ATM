package com.example.atm.service;

import com.example.atm.entity.ATM;
import com.example.atm.entity.Dollar;

public interface ATMService {
    ATM save(ATM atm);

    ATM findById(Long id);

    void addCashToATM(Long atmId, Dollar dollar, Long value);

    void getCashFromATM(Long atmId, Long accountNumber, Long value);

    void putCashOnAccount(Long atmId, Long accountNumber, Dollar dollar, Long value);

    void sendMoneyToBankAccount(Long numberOfYouAccount, Long numberOfAnotherAccount, Long value);
}
