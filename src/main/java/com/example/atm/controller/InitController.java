package com.example.atm.controller;

import javax.annotation.PostConstruct;

import com.example.atm.entity.Atm;
import com.example.atm.entity.BankAccount;
import com.example.atm.entity.Dollar;
import com.example.atm.entity.Role;
import com.example.atm.entity.User;
import com.example.atm.repository.BankAccountRepository;
import com.example.atm.service.AtmService;
import com.example.atm.service.BankAccountService;
import com.example.atm.service.RoleService;
import com.example.atm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
public class InitController {
    @Autowired
    private UserService userService;
    @Autowired
    private AtmService atmService;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void addInfo() {
        Role userRole = new Role();
        userRole.setRoleName("USER");
        Role adminRole = new Role();
        adminRole.setRoleName("ADMIN");
        roleService.save(userRole);
        roleService.save(adminRole);
        User user = new User();
        user.setName("Bob");
        user.setPassword(passwordEncoder.encode("123"));
        user.getRoles().add(userRole);
        user.getRoles().add(adminRole);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setNumber(123L);
        bankAccount.setCash(2000L);
        bankAccountService.save(bankAccount);
        user.getBankAccounts().add(bankAccount);
        userService.save(user);
        Atm atm = new Atm();
        atmService.save(atm);
        atmService.addCashToATM(atm.getId(), Dollar.DOLLARS100, 20L);
        atmService.addCashToATM(atm.getId(), Dollar.DOLLARS200, 10L);
        atmService.addCashToATM(atm.getId(), Dollar.DOLLARS500, 5L);
    }
}
