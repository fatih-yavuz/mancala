package com.fatih.service;

import com.fatih.exception.IllegalTurnException;
import com.fatih.model.Game;
import com.fatih.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TurnService {

    private final GameRepository gameRepository;

    @Autowired
    TurnService(final GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void validateTurn(final String id, final String turn) {
        final Game game = gameRepository.findById(id);
        if (!Objects.equals(game.getTurn(), turn)) {
            throw new IllegalTurnException(String.format("Player: %s is not allowed to play in this turn", turn));
        }
    }
}
