package com.fatih.repository;


import com.fatih.exception.GameNotFoundException;
import com.fatih.model.Game;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class InMemoryGameRepository implements GameRepository {

    private final Map<String, Game> gameStore = new HashMap<>();
    private int storedGameCount;

    @Override
    public Game findById(final String id) {
        final Game game = gameStore.get(id);
        if (Objects.isNull(game)) {
            throw new GameNotFoundException(String.format("Game with id: %s could not be found", id));
        }
        return game;
    }

    @Override
    public Game save(final Game game) {
        storedGameCount++;
        final String key = String.format("%d", storedGameCount);
        game.setId(key);
        gameStore.put(key, game);
        return game;
    }
}
