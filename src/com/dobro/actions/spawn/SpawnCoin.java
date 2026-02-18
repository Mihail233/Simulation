package com.dobro.actions.spawn;

import com.dobro.actions.SpawnProbability;
import com.dobro.actions.path.Path;
import com.dobro.models.Coin;
import com.dobro.models.Entity;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.ArrayList;
import java.util.Optional;

public class SpawnCoin extends Spawn {

    @Override
    public void execute(WorldMap worldMap) {
        ArrayList<Cell> previousSpawnLocations = worldMap.getCellsOfCertainType(SpawnDependency.COIN.getClazz());

        for (int indexRow = worldMap.getOriginWorldMap().getY(); indexRow < worldMap.getMaxWidthField(); indexRow++) {
            for (int indexColumn = worldMap.getOriginWorldMap().getX(); indexColumn < worldMap.getMaxLengthField(); indexColumn++) {
                Cell spawnLocation = new Cell(indexRow, indexColumn);
                Optional<? extends Entity> entity = worldMap.getEntity(spawnLocation);
                if (entity.isEmpty() && super.isPlaceEntity(worldMap.getSpawnRate(), SpawnProbability.COIN.getProbability())) {
                    if (!hasConnectionWithPreviousSpawnLocations(spawnLocation, previousSpawnLocations, worldMap)) {
                        continue;
                    }
                    worldMap.setEntity(spawnLocation, new Coin());
                    previousSpawnLocations.add(spawnLocation);
                }
            }
        }
    }

    public boolean hasConnectionWithPreviousSpawnLocations(Cell spawnLocation, ArrayList<Cell> previousSpawnLocations, WorldMap worldMap) {
        for (Cell previousSpawnLocation : previousSpawnLocations) {
            Path path = new Path(worldMap, spawnLocation, previousSpawnLocation);
            if (!path.isPathFound()) {
                return false;
            }
        }
        return true;
    }
}