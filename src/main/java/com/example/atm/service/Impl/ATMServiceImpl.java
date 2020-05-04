package com.example.atm.service.Impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.atm.entity.ATM;
import com.example.atm.entity.BankAccount;
import com.example.atm.entity.Dollar;
import com.example.atm.exception.ATMException;
import com.example.atm.repository.ATMRepository;
import com.example.atm.repository.BankAccountRepository;
import com.example.atm.service.ATMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ATMServiceImpl implements ATMService {
    @Autowired
    private ATMRepository atmRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public ATM save(ATM atm) {
        return atmRepository.save(atm);
    }

    @Override
    public ATM findById(Long id) {
        Optional<ATM> atm = atmRepository.findById(id);
        if (atm.isEmpty()) {
            throw new ATMException("Can`t find ATM by ID");
        }
        return atm.get();
    }

    @Override
    public void addCashToATM(Long atmId, Dollar dollar, Long value) {
        ATM atm = findById(atmId);
        atm.addDollars(dollar, value);
        save(atm);
    }

    @Override
    public void getCashFromATM(Long atmId, Long accountNumber, Long value) {
        ATM atm = findById(atmId);
        BankAccount bankAccount = bankAccountRepository.findByNumber(accountNumber);
        if (bankAccount.getCash() < value) {
            throw new ATMException("Not enough money on you account");
        } else if (getAtmCash(atm) < value) {
            throw new ATMException("Not enough money in ATM");
        } else if (value % 100 != 0) {
            throw new ATMException("Your amount is not a multiple of 100");
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
            throw new  ATMException("Not enough money on you account");
        }
        youBankAccount.setCash(youBankAccount.getCash() - value);
        anotherBankAccount.setCash(anotherBankAccount.getCash() + value);
        bankAccountRepository.save(youBankAccount);
        bankAccountRepository.save(anotherBankAccount);
    }

    private Long getAtmCash(ATM atm) {
        Long result = 0L;
        for (Map.Entry<Dollar, Long> entry : atm.getCash().entrySet()) {
            result += entry.getKey().getNominal() * entry.getValue();
        }
        return result;
    }
}
