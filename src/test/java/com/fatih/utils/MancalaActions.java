package com.fatih.utils;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class MancalaActions {
    private final ResultActions actions;

    public MancalaActions(final ResultActions actions) {
        this.actions = actions;
    }

    public MancalaActions expectBanksToBeEmpty() throws Exception {
        actions
                .andExpect(jsonPath("$.players[0].bank.seeds").value(0))
                .andExpect(jsonPath("$.players[1].bank.seeds").value(0));
        return this;
    }

    public MancalaActions expectPlayerIdsToBeFilled() throws Exception {
        actions
                .andExpect(jsonPath("$.players[0]").isNotEmpty())
                .andExpect(jsonPath("$.players[1]").isNotEmpty());
        return this;
    }

    public MancalaActions expectAllPitsToBeFull(final String playerKey) throws Exception {
        actions
                .andExpect(seedCountInPit(playerKey, 0).value(6))
                .andExpect(seedCountInPit(playerKey, 1).value(6))
                .andExpect(seedCountInPit(playerKey, 2).value(6))
                .andExpect(seedCountInPit(playerKey, 3).value(6))
                .andExpect(seedCountInPit(playerKey, 4).value(6))
                .andExpect(seedCountInPit(playerKey, 5).value(6));
        return this;
    }

    public MancalaActions expectSeedCountOf(final String player, final int pitIndex, final int expectedSeedCount)
            throws Exception {
        final String path = String.format("$.players[%s].pits[%d].seeds", player, pitIndex);
        actions.andExpect(
                jsonPath(path).value(expectedSeedCount));
        return this;
    }

    private JsonPathResultMatchers seedCountInPit(final String playerKey, final int index) {
        return jsonPath(String.format("$.%s.pits[%d].seeds", playerKey, index));
    }
}
