package com.dobro.actions;

import com.dobro.models.Coin;
import com.dobro.service.Cell;
import com.dobro.actions.path.Path;
import com.dobro.service.WorldMap;

import java.util.ArrayList;

public class SpawnCoin extends Spawn {
    private final ArrayList<Cell> previousSpawnLocations = new ArrayList<>();

    @Override
    public void spawnEntity(Cell spawnLocation, WorldMap worldMap) {
        if (worldMap.isEmptyCell(spawnLocation)) {
            if (super.isPlaceEntity(worldMap.getSpawnRate(), SpawnProbability.COIN.getProbability())) {
                if (!hasConnectionWithPreviousSpawn(worldMap, spawnLocation)) {
                    return;
                }
                worldMap.setEntity(spawnLocation, new Coin());
                previousSpawnLocations.add(spawnLocation);
            }
        }
    }

    public boolean hasConnectionWithPreviousSpawn(WorldMap worldMap, Cell spawnLocation) {
        for (Cell previousSpawnLocation : previousSpawnLocations) {
            Path path = new Path(worldMap, spawnLocation, previousSpawnLocation);
            if (!path.isPathFound()) {
                return false;
            }
        }
        return true;
    }
}
