package com.fatih;

import com.fatih.event.PlayerAttendedEvent;
import com.fatih.exception.NoRoomForAnotherPlayerException;
import com.fatih.service.EventManager;
import lombok.Getter;
import lombok.Setter;
import org.greenrobot.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class GameSession {

    private static final int MAX_PLAYER_COUNT = 2;
    private int playerCount;

    private EventManager eventManager;

    @Autowired
    GameSession(final EventManager eventManager) {
        this.eventManager = eventManager;
        registerToEvents();
    }

    private void registerToEvents() {
        eventManager.register(this);
    }

    @Subscribe
    public void onPlayerAttend(final PlayerAttendedEvent event) {
        incrementPlayerCount();
    }

    public void incrementPlayerCount() {
        if (MAX_PLAYER_COUNT == playerCount) {
            throw new NoRoomForAnotherPlayerException();
        }
        playerCount += 1;
    }

    public void reset() {
        playerCount = 0;
        eventManager.reset();
        registerToEvents();
    }
}
