package com.fatih;

import com.fatih.model.Game;
import com.fatih.model.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class DistributorTest {

    @Autowired
    private Distributor distributor;

    @Test
    void it_should_distribute_when_player_1_plays_for_index_0() {
        // Arrange
        final Game game = new Game();
        final int pitIndex = 0;

        // Act
        distributor.distribute(game.getPlayers(), game.getPlayingPlayer(), pitIndex);

        // Assert
        final Player player1 = game.getPlayers().get(0);
        assertEquals(1, player1.getBank().getSeeds());
        assertEquals(0, player1.getPits().get(0).getSeeds());
        assertEquals(7, player1.getPits().get(1).getSeeds());
        assertEquals(7, player1.getPits().get(2).getSeeds());
        assertEquals(7, player1.getPits().get(3).getSeeds());
        assertEquals(7, player1.getPits().get(4).getSeeds());
        assertEquals(7, player1.getPits().get(5).getSeeds());

        final Player player2 = game.getPlayers().get(1);
        assertEquals(0, player2.getBank().getSeeds());
        assertEquals(6, player2.getPits().get(0).getSeeds());
        assertEquals(6, player2.getPits().get(1).getSeeds());
        assertEquals(6, player2.getPits().get(2).getSeeds());
        assertEquals(6, player2.getPits().get(3).getSeeds());
        assertEquals(6, player2.getPits().get(4).getSeeds());
        assertEquals(6, player2.getPits().get(5).getSeeds());
    }

    @Test
    void it_should_distribute_when_player_1_plays_for_index_1() {
        // Arrange
        final Game game = new Game();
        final int pitIndex = 1;

        // Act
        distributor.distribute(game.getPlayers(), game.getPlayingPlayer(), pitIndex);

        // Assert
        final Player player1 = game.getPlayers().get(0);
        assertEquals(1, player1.getBank().getSeeds());
        assertEquals(6, player1.getPits().get(0).getSeeds());
        assertEquals(0, player1.getPits().get(1).getSeeds());
        assertEquals(7, player1.getPits().get(2).getSeeds());
        assertEquals(7, player1.getPits().get(3).getSeeds());
        assertEquals(7, player1.getPits().get(4).getSeeds());
        assertEquals(7, player1.getPits().get(5).getSeeds());

        final Player player2 = game.getPlayers().get(1);
        assertEquals(0, player2.getBank().getSeeds());
        assertEquals(7, player2.getPits().get(0).getSeeds());
        assertEquals(6, player2.getPits().get(1).getSeeds());
        assertEquals(6, player2.getPits().get(2).getSeeds());
        assertEquals(6, player2.getPits().get(3).getSeeds());
        assertEquals(6, player2.getPits().get(4).getSeeds());
        assertEquals(6, player2.getPits().get(5).getSeeds());
    }

    @Test
    void it_should_distribute_when_player_1_plays_for_index_0_then_index_1() {
        // Arrange
        final Game game = new Game();

        // Act
        distributor.distribute(game.getPlayers(), game.getPlayingPlayer(), 0);
        distributor.distribute(game.getPlayers(), game.getPlayingPlayer(), 1);

        // Assert
        final Player player1 = game.getPlayers().get(0);
        assertEquals(2, player1.getBank().getSeeds());
        assertEquals(0, player1.getPits().get(0).getSeeds());
        assertEquals(0, player1.getPits().get(1).getSeeds());
        assertEquals(8, player1.getPits().get(2).getSeeds());
        assertEquals(8, player1.getPits().get(3).getSeeds());
        assertEquals(8, player1.getPits().get(4).getSeeds());
        assertEquals(8, player1.getPits().get(5).getSeeds());

        final Player player2 = game.getPlayers().get(1);
        assertEquals(0, player2.getBank().getSeeds());
        assertEquals(7, player2.getPits().get(0).getSeeds());
        assertEquals(7, player2.getPits().get(1).getSeeds());
        assertEquals(6, player2.getPits().get(2).getSeeds());
        assertEquals(6, player2.getPits().get(3).getSeeds());
        assertEquals(6, player2.getPits().get(4).getSeeds());
        assertEquals(6, player2.getPits().get(5).getSeeds());
    }

//    @Test
//    void it_should_throw_exception_when_player1_attempts_to_play_on_empty_pit() {
//
//    }
//
//    @Test
//    void it_should_return_true_when_the_game_is_finished() {
//
//    }
}
