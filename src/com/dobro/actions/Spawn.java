package com.dobro.actions;

import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

abstract public class Spawn extends Action {

    public abstract void spawnEntity(Cell cell, WorldMap worldMap);

    @Override
    public void execute(WorldMap worldMap) {
        for (int indexRow = worldMap.getOriginWorldMap().getY(); indexRow < worldMap.getMaxWidthField(); indexRow++) {
            for (int indexColumn = worldMap.getOriginWorldMap().getX(); indexColumn < worldMap.getMaxLengthField(); indexColumn++) {
                Cell currentCell = new Cell(indexRow, indexColumn);
                spawnEntity(currentCell, worldMap);
            }
        }
    }

    public float getRandomNumber() {
        return (float) Math.random();
    }

    public boolean isPlaceEntity(float spawnRate, float spawnProbability) {
        float randomNumber = getRandomNumber();
        return randomNumber <= spawnRate * spawnProbability;
    }
}
