package com.fatih;

import com.fatih.model.Game;
import com.fatih.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    @Test
    void it_should_get_correct_playing_player_when_game_is_created() {
        // Arrange
        final Game game = new Game();

        // Act
        final Player playingPlayer = game.getPlayingPlayer();

        // Assert
        assertEquals(playingPlayer, game.getPlayers().get(0));
    }

    @Test
    void it_should_get_correct_playing_player_when_turn_is_changed() {
        // Arrange
        final Game game = new Game();
        final Player secondPlayer = game.getPlayers().get(1);
        game.setTurn(secondPlayer.getId());

        // Act
        final Player playingPlayer = game.getPlayingPlayer();

        // Assert
        assertEquals(playingPlayer, secondPlayer);
    }
}
