package com.dobro.actions.spawn;

import com.dobro.actions.Action;
import com.dobro.service.WorldMap;

abstract public class Spawn extends Action {
    public abstract void execute(WorldMap worldMap);

    public float getRandomNumber() {
        return (float) Math.random();
    }

    public boolean isPlaceEntity(float spawnRate, float spawnProbability) {
        float randomNumber = getRandomNumber();
        return randomNumber <= spawnRate * spawnProbability;
    }
}