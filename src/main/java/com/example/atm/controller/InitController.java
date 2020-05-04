package com.example.atm.controller;

import javax.annotation.PostConstruct;

import com.example.atm.entity.ATM;
import com.example.atm.entity.BankAccount;
import com.example.atm.entity.Dollar;
import com.example.atm.entity.User;
import com.example.atm.repository.BankAccountRepository;
import com.example.atm.service.Impl.ATMServiceImpl;
import com.example.atm.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class InitController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ATMServiceImpl atmService;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @PostConstruct
    public void addInfo() {
        User user = new User();
        user.setName("Bob");
        user.setPassword("123");
        BankAccount bankAccount = new BankAccount();
        bankAccount.setNumber(123L);
        bankAccount.setCash(2000L);
        bankAccountRepository.save(bankAccount);
        user.getBankAccounts().add(bankAccount);
        userService.save(user);
        ATM atm = new ATM();
        atmService.save(atm);
        atmService.addCashToATM(atm.getId(), Dollar.DOLLARS100, 20L);
        atmService.addCashToATM(atm.getId(), Dollar.DOLLARS200, 10L);
        atmService.addCashToATM(atm.getId(), Dollar.DOLLARS500, 5L);
    }
}
