package com.dobro.models;

import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.Optional;

abstract public class Creature extends Entity {
    private int speed;
    private int HealthPoints;

    public Creature(int speed) {
        this.speed = speed;
    }

    public abstract void makeTurn(Cell location, WorldMap worldMap);

    public void makeMove(Cell location, Cell nextCell, WorldMap worldMap) {
        Optional<? extends Entity> entity = worldMap.getEntity(location);
        //изменение карты во время того как по ней проходится CreaturesMakeTurn
        worldMap.removeEntity(location);
        worldMap.setEntity(nextCell, entity.get());
    }

    public int getSpeed() {
        return speed;
    }
}
