package com.fatih.model;

import lombok.Data;

@Data
public class Pit {
    public static final int INITIAL_SEED_COUNT = 6;
    private int seeds = INITIAL_SEED_COUNT;

    public void addSeed() {
        this.seeds++;
    }
}
