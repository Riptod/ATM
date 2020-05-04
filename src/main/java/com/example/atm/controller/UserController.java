package com.example.atm.controller;

import com.example.atm.dto.BankAccountDto;
import com.example.atm.dto.UserRegistrationDto;
import com.example.atm.entity.BankAccount;
import com.example.atm.entity.User;
import com.example.atm.repository.BankAccountRepository;
import com.example.atm.service.BankAccountService;
import com.example.atm.service.Impl.BankAccountServiceImpl;
import com.example.atm.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private BankAccountServiceImpl bankAccountService;

    @PostMapping(value = "addUser")
    public String addUser(@RequestBody UserRegistrationDto requestDto) {
        User user = new User();
        user.setName(requestDto.getName());
        user.setPassword(requestDto.getPassword());
        userService.save(user);
        return "User added successfully";
    }

    @PostMapping(value = "/addAccount")
    public String createBankAccount(@RequestBody BankAccountDto requestDto) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setNumber(requestDto.getNumber());
        bankAccount.setCash(requestDto.getCash());
        bankAccountService.save(bankAccount);
        User user = userService.findUserByName(requestDto.getUserName());
        user.getBankAccounts().add(bankAccount);
        userService.save(user);
        return "User bank account was added successfully";
    }
}
