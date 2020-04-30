package com.example.atm.entity;

public enum Dollar {
    DOLLARS100(100L), DOLLARS200(200L), DOLLARS500(500L);

    private Long nominal;

    Dollar(Long nominal) {
        this.nominal = nominal;
    }

    public Long getNominal() {
        return nominal;
    }
}
