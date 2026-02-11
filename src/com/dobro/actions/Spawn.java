package com.dobro.actions;

import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

abstract public class Spawn extends Action {

    public abstract void spawnEntity(Cell spawnLocation, WorldMap worldMap);

    @Override
    public void execute(WorldMap worldMap) {
        for (int indexRow = worldMap.getOriginWorldMap().getY(); indexRow < worldMap.getMaxWidthField(); indexRow++) {
            for (int indexColumn = worldMap.getOriginWorldMap().getX(); indexColumn < worldMap.getMaxLengthField(); indexColumn++) {
                Cell currentSpawnLocation = new Cell(indexRow, indexColumn);
                spawnEntity(currentSpawnLocation, worldMap);
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
