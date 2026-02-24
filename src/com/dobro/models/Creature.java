package com.dobro.models;

import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.Optional;

abstract public class Creature extends Entity {
    private final int speed;
    private final int interactionDistance;
    private int healthPoints;

    public Creature(int interactionDistance, int speed, int healthPoints) {
        this.interactionDistance = interactionDistance;
        this.speed = speed;
        this.healthPoints = healthPoints;
    }

    public abstract void makeTurn(Cell location, WorldMap worldMap);

    public void makeMove(Cell location, Cell nextLocation, WorldMap worldMap) {
        System.out.printf("Существо %s переместилось на %s", location, nextLocation);
        Optional<? extends Entity> entity = worldMap.getEntity(location);
        worldMap.removeEntity(location);
        worldMap.setEntity(nextLocation, entity.get());
    }

    public int getInteractionDistance() {
        return interactionDistance;
    }

    public int getSpeed() {
        return speed;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void initDeath(Cell location, WorldMap worldMap) {
        worldMap.removeEntity(location);
        System.out.printf("Существо умерло %s", location);
    }
}
