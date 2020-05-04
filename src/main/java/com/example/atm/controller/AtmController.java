package com.example.atm.controller;

import com.example.atm.dto.CashFromAtmDto;
import com.example.atm.dto.CashOnAccountDto;
import com.example.atm.dto.CashToAtmDto;
import com.example.atm.dto.SendMoneyToBankAccountDto;
import com.example.atm.service.Impl.ATMServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atm")
public class AtmController {

    @Autowired
    private ATMServiceImpl atmService;

    @PostMapping(value = "/addCash")
    public String addCashToAtm(@RequestBody CashToAtmDto requestDto) {
        atmService.addCashToATM(requestDto.getAtmId(), requestDto.getDollar(),
                requestDto.getValue());
        return "Cash was added successfully";
    }

    @PostMapping(value = "/getCashFromAccount")
    public String getCashFromAtm(@RequestBody CashFromAtmDto requestDto) {
        atmService.getCashFromATM(requestDto.getAtmId(), requestDto.getAccountNumber(),
                requestDto.getValue());
        return "Cash was got successfully";
    }

    @PostMapping(value = "/putCashOnAccount")
    public String putCashOnAccount(@RequestBody CashOnAccountDto requestDto) {
        atmService.putCashOnAccount(requestDto.getAtmId(), requestDto.getAccountNumber(),
                requestDto.getDollar(), requestDto.getValue());
        return "Cash was added successfully on you account - " + requestDto.getAccountNumber();
    }

    @PostMapping(value = "/sendMoneyToAccount")
    public String sendMoneyToBankAccount(@RequestBody SendMoneyToBankAccountDto requestDto) {
        atmService.sendMoneyToBankAccount(requestDto.getNumberOfYouAccount(),
                requestDto.getNumberOfAnotherAccount(), requestDto.getValue());
        return "Cash was successfully sent to account - " + requestDto.getNumberOfAnotherAccount();
    }
}
