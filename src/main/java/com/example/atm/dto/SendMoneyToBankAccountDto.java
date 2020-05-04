package com.example.atm.dto;

import lombok.Data;

@Data
public class SendMoneyToBankAccountDto {
    private Long numberOfYouAccount;
    private Long numberOfAnotherAccount;
    private Long value;
}
