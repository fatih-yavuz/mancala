package com.fatih.service;

public interface EventManager {

    void register(Object subscriber);

    void fire(Object event);

    void reset();
}
