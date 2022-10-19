package com.fatih.model;

import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Game {
    private String id;
    private List<Player> players = Arrays.asList(new Player(), new Player());
    private Map<String, Player> playerMap = new HashMap<>();
    private String turn;

    public Game() {
        initializeTurn();
        initializePlayerMap();
    }

    private void initializeTurn() {
        turn = players.get(0).getId();
    }

    private void initializePlayerMap() {
        for (final Player player : players) {
            playerMap.put(player.getId(), player);
        }
    }

    public Player getPlayingPlayer() {
        return playerMap.get(this.getTurn());
    }
}
