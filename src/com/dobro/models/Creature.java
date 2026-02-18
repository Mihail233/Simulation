package com.dobro.models;

import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

abstract public class Creature extends Entity {
    private int speed;
    private int HealthPoints;

    public abstract void makeTurn(Cell location, WorldMap worldMap);

    public void makeMove(Cell cell) {
        System.out.printf("Переместился на %d %d\n", cell.getX(), cell.getY());
    }
}
