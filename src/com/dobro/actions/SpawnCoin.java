package com.dobro.actions;

import com.dobro.models.Coin;
import com.dobro.service.Cell;
import com.dobro.service.Path;
import com.dobro.service.WorldMap;

import java.util.ArrayList;

public class SpawnCoin extends Spawn {
    private final ArrayList<Cell> previousSpawnLocations = new ArrayList<>();

    @Override
    public void spawnEntity(Cell currentSpawnLocation, WorldMap worldMap) {
        if (worldMap.isEmptyCell(currentSpawnLocation)) {
            if (super.isPlaceEntity(worldMap.getSpawnRate(), SpawnProbabilities.COIN.getSpawnProbability())) {
                if (!hasConnectionWithPreviousSpawn(worldMap, currentSpawnLocation)) {
                    return;
                }
                worldMap.setEntity(currentSpawnLocation, new Coin());
                previousSpawnLocations.add(currentSpawnLocation);
            }
        }
    }

    public boolean hasConnectionWithPreviousSpawn(WorldMap worldMap, Cell currentSpawnLocation) {
        for (Cell previousSpawnLocation : previousSpawnLocations) {
            Path path = new Path(worldMap, currentSpawnLocation, previousSpawnLocation);
            if (!path.isPathFound()) {
                return false;
            }
        }
        return true;
    }
}
