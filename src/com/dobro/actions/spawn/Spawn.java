package com.dobro.actions.spawn;

import com.dobro.Cell;
import com.dobro.actions.Action;
import com.dobro.WorldMap;
import com.dobro.entity.Coin;
import com.dobro.entity.Entity;

import java.util.Optional;

abstract public class Spawn extends Action {
    private final float probability;

    public Spawn(float probability) {
        this.probability = probability;
    }

    public abstract void spawn(WorldMap worldMap, Cell spawnCell);

    @Override
    public void execute(WorldMap worldMap) {
        int width = worldMap.getWidth();
        int height = worldMap.getHeight();
        int startRow = worldMap.getOriginWorldMap().x();
        int startColumn = worldMap.getOriginWorldMap().y();

        for (int indexRow = startRow; indexRow < width; indexRow++) {
            for (int indexColumn = startColumn; indexColumn < height; indexColumn++) {
                Cell spawnCell = new Cell(indexRow, indexColumn);
                Optional<? extends Entity> entity = worldMap.getEntity(spawnCell);
                if (entity.isEmpty() && isPlaceEntity(worldMap.getSpawnRate(), probability)) {
                    spawn(worldMap, spawnCell);
                }
            }
        }
    };

    protected float getRandomNumber() {
        return (float) Math.random();
    }

    private boolean isPlaceEntity(float spawnRate, float spawnProbability) {
        float randomNumber = getRandomNumber();
        return randomNumber <= spawnRate * spawnProbability;
    }
}