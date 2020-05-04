package com.example.atm.dto;

import com.example.atm.entity.Dollar;
import lombok.Data;

@Data
public class CashToAtmDto {
    private Long atmId;
    private Dollar dollar;
    private Long value;
}
