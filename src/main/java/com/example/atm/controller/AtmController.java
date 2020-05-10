package com.example.atm.controller;

import com.example.atm.dto.CashFromAtmDto;
import com.example.atm.dto.CashOnAccountDto;
import com.example.atm.dto.CashToAtmDto;
import com.example.atm.dto.SendMoneyToBankAccountDto;
import com.example.atm.exception.RequestCustomException;
import com.example.atm.service.AtmService;
import com.example.atm.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atm")
public class AtmController {

    @Autowired
    private AtmService atmService;
    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping(value = "/addCash")
    public void addCashToAtm(@RequestBody CashToAtmDto requestDto) {
        atmService.addCashToATM(requestDto.getAtmId(), requestDto.getDollar(),
                requestDto.getValue());
    }

    @PostMapping(value = "/getCashFromAccount")
    public void getCashFromAtm(@RequestBody CashFromAtmDto requestDto,
                                 Authentication authentication) {
        if (!authorizationService.checkAccount(authentication.getName(), requestDto.getAccountNumber())) {
            throw new RequestCustomException("Its not you account");
        }
        atmService.getCashFromATM(requestDto.getAtmId(), requestDto.getAccountNumber(),
                requestDto.getValue());
    }

    @PostMapping(value = "/putCashOnAccount")
            public String putCashOnAccount(@RequestBody CashOnAccountDto requestDto) {
        atmService.putCashOnAccount(requestDto.getAtmId(), requestDto.getAccountNumber(),
                requestDto.getDollar(), requestDto.getValue());
        return "Cash was added successfully on you account - " + requestDto.getAccountNumber();
    }

    @PostMapping(value = "/sendMoneyToAccount")
    public void sendMoneyToBankAccount(@RequestBody SendMoneyToBankAccountDto requestDto,
                                         Authentication authentication) {
        if (!authorizationService.checkAccount(authentication.getName(), requestDto.getNumberOfYouAccount())) {
            throw new RequestCustomException("Its not you account");
        }
        atmService.sendMoneyToBankAccount(requestDto.getNumberOfYouAccount(),
                requestDto.getNumberOfAnotherAccount(), requestDto.getValue());
    }
}
