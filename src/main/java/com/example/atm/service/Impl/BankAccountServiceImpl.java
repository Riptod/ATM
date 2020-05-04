package com.example.atm.service.Impl;

import com.example.atm.entity.BankAccount;
import com.example.atm.repository.BankAccountRepository;
import com.example.atm.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;

public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public BankAccount save(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }
}
