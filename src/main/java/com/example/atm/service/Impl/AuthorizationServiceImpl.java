package com.example.atm.service.Impl;

import java.util.List;

import com.example.atm.entity.BankAccount;
import com.example.atm.entity.User;
import com.example.atm.service.AuthorizationService;
import com.example.atm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Autowired
    private UserService userService;

    @Override
    public boolean checkAccount(String userName, Long accountNumber) {
        User user = userService.findUserByName(userName);
        List<BankAccount> bankAccounts = user.getBankAccounts();
        for (BankAccount b : bankAccounts) {
            if (b.getNumber().equals(accountNumber)) {
                return true;
            }
        }
        return false;
    }
}
