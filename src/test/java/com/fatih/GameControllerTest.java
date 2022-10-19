package com.fatih;

import com.fatih.event.PlayerAttendedEvent;
import com.fatih.model.Game;
import com.fatih.repository.GameRepository;
import com.fatih.service.EventManager;
import com.fatih.utils.MancalaActions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameSession gameSession;

    @Autowired
    private EventManager eventManager;

    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        gameSession.reset();
    }

    @Test
    void it_should_return_ok_when_game_is_created() throws Exception {
        // Act
        ResultActions actions = mockMvc.perform(post("/game"));

        // Assert
        actions = actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.turn").isNotEmpty());

        new MancalaActions(actions)
                .expectPlayerIdsToBeFilled()
                .expectBanksToBeEmpty()
                .expectAllPitsToBeFull("players[0]")
                .expectAllPitsToBeFull("players[1]");
    }

    @Test
    void it_should_return_error_when_a_new_player_tries_to_attend_when_the_room_is_full() throws Exception {
        eventManager.fire(new PlayerAttendedEvent());
        ResultActions actions = mockMvc.perform(post("/game"));

        actions = actions
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath("$.exception").isNotEmpty());
    }

    @Test
    void it_should_return_ok_when_game_is_found() throws Exception {
        // Arrange
        Game game = new Game();

        // Act
        game = gameRepository.save(game);

        // Assert
        ResultActions actions = mockMvc.perform(get(String.format("/game/%s", game.getId())));
        actions = actions
                .andExpect(status().isOk());
    }

    @Test
    void it_should_return_not_found_when_game_is_not_found() throws Exception {
        // Arrange
        Game game = new Game();

        // Act
        game = gameRepository.save(game);

        // Assert
        final String nonExistentId = String.format("%s-dummy_suffix", game.getId());
        ResultActions actions = mockMvc
                .perform(get(String.format("/game/%s", nonExistentId)));
        actions = actions
                .andExpect(status().isNotFound());
    }
}
