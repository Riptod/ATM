package com.example.atm.dto;

import lombok.Data;

@Data
public class CashFromAtmDto {
    private Long atmId;
    private Long accountNumber;
    private Long value;
}
