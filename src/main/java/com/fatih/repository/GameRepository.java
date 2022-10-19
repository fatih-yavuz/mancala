package com.fatih.repository;

import com.fatih.model.Game;

public interface GameRepository {

    Game findById(String id);

    Game save(Game game);

}
