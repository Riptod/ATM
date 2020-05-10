package com.example.atm.dto;

import com.example.atm.entity.Dollar;
import lombok.Data;

@Data
public class CashOnAccountDto {
    private Long atmId;
    private Long accountNumber;
    private Dollar dollar;
    private Long value;
}
