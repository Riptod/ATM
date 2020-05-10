package com.example.atm.controller;

import com.example.atm.dto.BankAccountDto;
import com.example.atm.dto.UserRegistrationDto;
import com.example.atm.entity.BankAccount;
import com.example.atm.entity.Role;
import com.example.atm.entity.User;
import com.example.atm.service.BankAccountService;
import com.example.atm.service.RoleService;
import com.example.atm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/addUser")
    public void addUser(@RequestBody UserRegistrationDto requestDto) {
        Role userRole = roleService.findRoleByRoleName("USER");
        User user = new User();
        user.setName(requestDto.getName());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.getRoles().add(userRole);
        userService.save(user);
    }

    @PostMapping(value = "/addAccount")
    public void createBankAccount(@RequestBody BankAccountDto requestDto) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setNumber(requestDto.getNumber());
        bankAccount.setCash(requestDto.getCash());
        bankAccountService.save(bankAccount);
        User user = userService.findUserByName(requestDto.getUserName());
        user.getBankAccounts().add(bankAccount);
        userService.save(user);
    }
}
