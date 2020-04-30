package com.example.atm.entity;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyEnumerated;

import lombok.Data;

@Data
@Entity
public class ATM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    @CollectionTable(name = "dollars_in_atm")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<Dollar, Long> cash = new HashMap<>();

    public ATM() {
        cash.put(Dollar.DOLLARS100, 0L);
        cash.put(Dollar.DOLLARS200, 0L);
        cash.put(Dollar.DOLLARS500, 0L);
    }

    public Map<Dollar, Long> getCash() {
        return cash;
    }

    public void setCash(Map<Dollar, Long> cash) {
        this.cash = cash;
    }

    public void addDollars(Dollar dollar, Long quantity) {
        cash.put(dollar, cash.get(dollar) + quantity);
    }

    public void getDollars(Dollar dollar, Long quantity) {
        cash.put(dollar, cash.get(dollar) - quantity);
    }
}
