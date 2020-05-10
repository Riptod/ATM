package com.example.atm.service;

import com.example.atm.entity.Atm;
import com.example.atm.entity.Dollar;

public interface AtmService {
    Atm save(Atm atm);

    Atm findById(Long id);

    void addCashToATM(Long atmId, Dollar dollar, Long value);

    void getCashFromATM(Long atmId, Long accountNumber, Long value);

    void putCashOnAccount(Long atmId, Long accountNumber, Dollar dollar, Long value);

    void sendMoneyToBankAccount(Long numberOfYouAccount, Long numberOfAnotherAccount, Long value);
}
