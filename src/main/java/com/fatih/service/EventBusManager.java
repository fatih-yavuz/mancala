package com.fatih.service;

import org.greenrobot.eventbus.EventBus;
import org.springframework.stereotype.Component;

@Component
public class EventBusManager implements EventManager {

    private EventBus eventBus;

    EventBusManager() {
        init();
    }

    private void init() {
        eventBus = EventBus.builder().throwSubscriberException(true).build();
    }

    @Override
    public void register(final Object subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public void fire(final Object event) {
        eventBus.post(event);
    }

    public void reset() {
        init();
    }
}
