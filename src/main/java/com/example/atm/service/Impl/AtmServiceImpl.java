package com.example.atm.service.Impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.atm.entity.Atm;
import com.example.atm.entity.BankAccount;
import com.example.atm.entity.Dollar;
import com.example.atm.exception.RequestCustomException;
import com.example.atm.repository.AtmRepository;
import com.example.atm.repository.BankAccountRepository;
import com.example.atm.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtmServiceImpl implements AtmService {
    @Autowired
    private AtmRepository atmRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public Atm save(Atm atm) {
        return atmRepository.save(atm);
    }

    @Override
    public Atm findById(Long id) {
        Optional<Atm> atm = atmRepository.findById(id);
        if (!atm.isPresent()) {
            throw new RequestCustomException("Can`t find ATM by Id - " + id);
        }
        return atm.get();
    }

    @Override
    public void addCashToATM(Long atmId, Dollar dollar, Long value) {
        Atm atm = findById(atmId);
        atm.addDollars(dollar, value);
        save(atm);
    }

    @Override
    public void getCashFromATM(Long atmId, Long accountNumber, Long value) {
        Atm atm = findById(atmId);
        BankAccount bankAccount = bankAccountRepository.findByNumber(accountNumber);
        if (bankAccount.getCash() < value) {
            throw new RequestCustomException("Not enough money on you account");
        } else if (getAtmCash(atm) < value) {
            throw new RequestCustomException("Not enough money in ATM");
        } else if (value % 100 != 0) {
            throw new RequestCustomException("Your amount is not a multiple of 100");
        }
        Map<Dollar, Long> resultCash = new HashMap<>();
        bankAccount.setCash(bankAccount.getCash() - value);
        while (value != 0) {
            if (value >= 500 && atm.getCash().get(Dollar.DOLLARS500) > 0) {
                atm.getDollars(Dollar.DOLLARS500, 1L);
                value -= 500;
            } else if (value >= 200 && atm.getCash().get(Dollar.DOLLARS200) > 0) {
                atm.getDollars(Dollar.DOLLARS200, 1L);
                value -= 200;
            } else if (value >= 100 && atm.getCash().get(Dollar.DOLLARS100) > 0) {
                atm.getDollars(Dollar.DOLLARS100, 1L);
                value -= 100;
            }
        }
        save(atm);
    }

    @Override
    public void putCashOnAccount(Long atmId, Long accountNumber, Dollar dollar, Long value) {
        BankAccount bankAccount = bankAccountRepository.findByNumber(accountNumber);
        addCashToATM(atmId, dollar, value);
        bankAccount.setCash(bankAccount.getCash() + value * dollar.getNominal());
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void sendMoneyToBankAccount(Long numberOfYouAccount, Long numberOfAnotherAccount, Long value) {
        BankAccount youBankAccount = bankAccountRepository.findByNumber(numberOfYouAccount);
        BankAccount anotherBankAccount = bankAccountRepository.findByNumber(numberOfAnotherAccount);
        if (youBankAccount.getCash() < value) {
            throw new RequestCustomException("Not enough money on you account");
        }
        youBankAccount.setCash(youBankAccount.getCash() - value);
        anotherBankAccount.setCash(anotherBankAccount.getCash() + value);
        bankAccountRepository.save(youBankAccount);
        bankAccountRepository.save(anotherBankAccount);
    }

    private Long getAtmCash(Atm atm) {
        Long result = 0L;
        for (Map.Entry<Dollar, Long> entry : atm.getCash().entrySet()) {
            result += entry.getKey().getNominal() * entry.getValue();
        }
        return result;
    }
}
