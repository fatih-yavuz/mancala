package com.fatih;

import com.fatih.model.Pit;
import com.fatih.model.Player;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Distributor {

    public static final int PIT_COUNT = 6;
    private int hand;
    private Player currentPlayer;

    public void distribute(final List<Player> players, final Player playingPlayer, final int pitIndex) {
        final Pit pit = playingPlayer.getPits().get(pitIndex);
        grabSeedsFrom(pit);
        currentPlayer = playingPlayer;
        int nextPitIndex = pitIndex + 1;
        while (hasSeedsInHand()) {
            if (isPlayingPlayersBank(playingPlayer, nextPitIndex)) {
                currentPlayer.getBank().addSeed();
                hand--;
                switchPlayingPlayer(players);
                nextPitIndex = 0;
            } else if (isOtherPlayersBank(playingPlayer, nextPitIndex)) {
                switchPlayingPlayer(players);
                nextPitIndex = 0;
            } else {
                addSeedToPitAtIndex(nextPitIndex);
                nextPitIndex = nextPitIndex + 1;
            }
        }

    }

    private void grabSeedsFrom(final Pit pit) {
        hand = pit.getSeeds();
        pit.setSeeds(0);
    }

    private boolean hasSeedsInHand() {
        return hand != 0;
    }

    private boolean isPlayingPlayersBank(final Player playingPlayer, final int nextPitIndex) {
        return nextPitIndex == PIT_COUNT && currentPlayer == playingPlayer;
    }

    private void switchPlayingPlayer(final List<Player> players) {
        currentPlayer = getNextPlayer(players);
    }

    private boolean isOtherPlayersBank(final Player playingPlayer, final int nextPitIndex) {
        return nextPitIndex == PIT_COUNT && currentPlayer != playingPlayer;
    }

    private void addSeedToPitAtIndex(final int nextPitIndex) {
        final Pit nextPit = currentPlayer.getPits().get(nextPitIndex);
        nextPit.addSeed();
        hand--;
    }

    private Player getNextPlayer(final List<Player> players) {
        final int currentPlayerIndex = players.indexOf(currentPlayer);
        final int nextPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return players.get(nextPlayerIndex);
    }
}
