package com.fatih;

import com.fatih.model.Game;
import com.fatih.model.PlayRequest;
import com.fatih.model.Player;
import com.fatih.repository.GameRepository;
import com.fatih.utils.MancalaActions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlayControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameSession gameSession;

    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        gameSession.reset();
    }

    @Test
    void it_should_return_not_found_when_game_is_not_found() throws Exception {
        // Act
        ResultActions actions = mockMvc.perform(put(String.format("/play/%s", "abc"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(
                        PlayRequest.builder()
                                .playerId("123")
                                .pitIndex(0)
                                .build())
                ));

        // Assert
        actions = actions
                .andExpect(status().isNotFound());
    }

    @Test
    void it_should_return_conflict_when_a_player_attempts_play_without_correct_turn() throws Exception {
        // Arrange
        final Game game = createGame();

        // Act
        ResultActions actions = mockMvc.perform(createContent(game, 1, 0));

        // Assert
        actions = actions
                .andExpect(status().isConflict());
    }

    private Game createGame() {
        Game game = new Game();
        final Player player1 = new Player("1");
        final Player player2 = new Player("2");
        game.setPlayers(Arrays.asList(player1, player2));
        game.setTurn(game.getPlayers().get(0).getId());

        game = gameRepository.save(game);
        return game;
    }

    private MockHttpServletRequestBuilder createContent(final Game game, final int playerIndex, final int pitIndex)
            throws JsonProcessingException {
        return put(String.format("/play/%s", game.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(
                        PlayRequest.builder()
                                .playerId(game.getPlayers().get(playerIndex).getId())
                                .pitIndex(pitIndex)
                                .build())
                );
    }

    @Test
    void it_should_update_the_game_when_a_player_plays_a_valid_rule() throws Exception {
        // Arrange
        final Game game = createGame();
        final JsonObject response = ResourceUtil.readAndParse("sample-player1-played-pit-0.json");

        final JsonArray pitsForPlayer1 = getPitsFor(response, 0);
        final JsonArray pitsForPlayer2 = getPitsFor(response, 1);

        // Act
        final ResultActions actions = mockMvc
                .perform(createContent(game, 0, 0))
                .andExpect(jsonPath("$.players[0]").isNotEmpty());


        // Assert
        new MancalaActions(actions.andExpect(status().isOk()))
                .expectSeedCountOf("0", 0, getSeedCountOf(pitsForPlayer1, 0))
                .expectSeedCountOf("0", 1, getSeedCountOf(pitsForPlayer1, 1))
                .expectSeedCountOf("0", 2, getSeedCountOf(pitsForPlayer1, 2))
                .expectSeedCountOf("0", 3, getSeedCountOf(pitsForPlayer1, 3))
                .expectSeedCountOf("0", 4, getSeedCountOf(pitsForPlayer1, 4))
                .expectSeedCountOf("0", 5, getSeedCountOf(pitsForPlayer1, 5));
//                .expectSeedCountOf("player2", 0, getSeedCountOf(pitsForPlayer2, 0))
//                .expectSeedCountOf("player2", 1, getSeedCountOf(pitsForPlayer2, 1))
//                .expectSeedCountOf("player2", 2, getSeedCountOf(pitsForPlayer2, 2))
//                .expectSeedCountOf("player2", 3, getSeedCountOf(pitsForPlayer2, 3))
//                .expectSeedCountOf("player2", 4, getSeedCountOf(pitsForPlayer2, 4))
//                .expectSeedCountOf("player2", 5, getSeedCountOf(pitsForPlayer2, 5));
    }

    private JsonArray getPitsFor(final JsonObject response, final int player) {
        return response.get("players").getAsJsonArray().get(player).getAsJsonObject().get("pits").getAsJsonArray();
    }

    private int getSeedCountOf(final JsonArray pits, final int pitIndex) {
        return pits.get(pitIndex).getAsJsonObject().get("seeds").getAsInt();
    }
}
