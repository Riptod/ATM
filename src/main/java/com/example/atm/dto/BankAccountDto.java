package com.example.atm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BankAccountDto {
    private String userName;
    private Long number;
    private Long cash;
}
