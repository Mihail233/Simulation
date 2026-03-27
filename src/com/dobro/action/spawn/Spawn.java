package com.dobro.action.spawn;

import com.dobro.Cell;
import com.dobro.worldmap.WorldMap;
import com.dobro.action.Action;

abstract public class Spawn extends Action {
    private final float probability;

    public Spawn(float probability) {
        this.probability = probability;
    }

    protected abstract void spawn(WorldMap worldMap, Cell spawnCell);

    @Override
    public void execute(WorldMap worldMap) {
        int width = worldMap.getWidth();
        int height = worldMap.getHeight();
        int startRow = worldMap.getOriginWorldMap().x();
        int startColumn = worldMap.getOriginWorldMap().y();

        for (int indexRow = startRow; indexRow < width; indexRow++) {
            for (int indexColumn = startColumn; indexColumn < height; indexColumn++) {
                Cell spawnCell = new Cell(indexRow, indexColumn);
                boolean isEmptyCell = worldMap.getEntity(spawnCell).isEmpty();
                if (isSatisfySpawnConditions(isEmptyCell, worldMap)) {
                    spawn(worldMap, spawnCell);
                }
            }
        }
    }

    private boolean isSatisfySpawnConditions(boolean isEmptyCell, WorldMap worldMap) {
        return isEmptyCell && isPlaceEntity(worldMap.getSpawnRate(), probability);
    }

    protected float getRandomNumber() {
        return (float) Math.random();
    }

    private boolean isPlaceEntity(float spawnRate, float spawnProbability) {
        float randomNumber = getRandomNumber();
        return randomNumber <= spawnRate * spawnProbability;
    }
}