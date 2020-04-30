package com.example.atm.controller;

import javax.annotation.PostConstruct;

import com.example.atm.entity.ATM;
import com.example.atm.entity.BankAccount;
import com.example.atm.entity.Dollar;
import com.example.atm.entity.User;
import com.example.atm.service.Impl.ATMServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AtmController {

    @Autowired
    private ATMServiceImpl atmService;

    @PostConstruct
    void initial() {
        ATM atm = new ATM();
        atm.addDollars(Dollar.DOLLARS100, 10L);
        atm.addDollars(Dollar.DOLLARS200, 10L);
        atm.addDollars(Dollar.DOLLARS500, 2L);
        BankAccount bankAccountUser = new BankAccount();
        bankAccountUser.setNumber(123L);
        bankAccountUser.setCash(2000L);
        User user = new User();
        user.setName("User");
        user.setPassword("123");
        user.getBankAccounts().add(bankAccountUser);
        atmService.getCashFromATM(atm, bankAccountUser, 1700L);

    }
}
