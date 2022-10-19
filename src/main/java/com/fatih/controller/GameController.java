package com.fatih.controller;

import com.fatih.GameSession;
import com.fatih.event.PlayerAttendedEvent;
import com.fatih.model.Game;
import com.fatih.repository.GameRepository;
import com.fatih.service.EventManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("game")
public class GameController {


    @SuppressWarnings({"squid:S1068", "unused", "FieldCanBeLocal"})
    private final GameSession gameSession;

    private final EventManager eventManager;
    private final GameRepository gameRepository;

    @Autowired
    GameController(final GameSession gameSession, final EventManager eventManager,
            final GameRepository gameRepository) {
        this.gameSession = gameSession;
        this.eventManager = eventManager;
        this.gameRepository = gameRepository;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity create() {
        eventManager.fire(new PlayerAttendedEvent());
        eventManager.fire(new PlayerAttendedEvent());
        Game game = new Game();
        game = gameRepository.save(game);
        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }

    @GetMapping(produces = "application/json", path = "/{id}")
    public ResponseEntity<Game> find(@PathVariable final String id) {
        return new ResponseEntity<>(gameRepository.findById(id), HttpStatus.OK);
    }
}
