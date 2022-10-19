package com.fatih.controller;

import com.fatih.model.Game;
import com.fatih.model.PlayRequest;
import com.fatih.model.Player;
import com.fatih.repository.GameRepository;
import com.fatih.service.TurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("play")
@RestController
public class PlayController {

    private final TurnService turnService;

    private final GameRepository gameRepository;

    @Autowired
    PlayController(final TurnService turnService, final GameRepository gameRepository) {
        this.turnService = turnService;
        this.gameRepository = gameRepository;
    }

    @PutMapping(produces = "application/json", path = "/{id}")
    public ResponseEntity play(@PathVariable final String id, @RequestBody final PlayRequest request) {
        turnService.validateTurn(id, request.getPlayerId());
        final Game game = gameRepository.findById(id);

        final Player player1 = game.getPlayers().get(0);
        player1.getPits().get(0).setSeeds(0);
        player1.getPits().get(1).setSeeds(7);
        player1.getPits().get(2).setSeeds(7);
        player1.getPits().get(3).setSeeds(7);
        player1.getPits().get(4).setSeeds(7);
        player1.getPits().get(5).setSeeds(7);

        return new ResponseEntity<>(game, HttpStatus.OK);
    }

}
