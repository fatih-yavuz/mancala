package com.fatih.model;

import lombok.Data;

@Data
public class Bank {
    private int seeds;

    public void addSeed() {
        this.seeds++;
    }
}
