package com.example.atm.service.Impl;

import java.util.HashMap;
import java.util.Map;

import com.example.atm.entity.ATM;
import com.example.atm.entity.BankAccount;
import com.example.atm.entity.Dollar;
import com.example.atm.exception.ATMException;
import com.example.atm.service.ATMService;
import org.springframework.stereotype.Service;

@Service
public class ATMServiceImpl implements ATMService {
    @Override
    public void addCashToATM(ATM atm, Long dollar100, Long dollar200, Long dollar500) {
        atm.addDollars(Dollar.DOLLARS100, dollar100);
        atm.addDollars(Dollar.DOLLARS200, dollar200);
        atm.addDollars(Dollar.DOLLARS500, dollar500);
    }

    @Override
    public void getCashFromATM(ATM atm, BankAccount bankAccount, Long value) {
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
    }

    private Long getAtmCash(ATM atm) {
        Long result = 0L;
        for (Map.Entry<Dollar, Long> entry : atm.getCash().entrySet()) {
            result += entry.getKey().getNominal() * entry.getValue();
        }
        return result;
    }
}
