package com.fatih.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Player {

    public static final int PIT_COUNT = 6;
    private static int playerCount = 1;
    private String id;

    private Bank bank = new Bank();
    private List<Pit> pits = new ArrayList<>();


    public Player(final String id) {
        this();
        this.id = id;
    }

    public Player() {
        this.id = String.format("100%d", playerCount);
        playerCount++;
        initializePits();
    }

    private void initializePits() {
        for (int i = 0; i < PIT_COUNT; i++) {
            pits.add(new Pit());
        }
    }
}
