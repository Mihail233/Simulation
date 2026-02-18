package com.dobro.actions.spawn;

import com.dobro.actions.path.Path;
import com.dobro.models.Entity;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.ArrayList;
import java.util.Optional;

public abstract class SpawnCreature extends Spawn {

    public <T extends Entity> void spawnCreature(WorldMap worldMap, Class<? extends Entity> clazz, float probability, T creature) {
        ArrayList<Cell> previousSpawnLocations = worldMap.getCellsOfCertainType(clazz);

        for (int indexRow = worldMap.getOriginWorldMap().getY(); indexRow < worldMap.getMaxWidthField(); indexRow++) {
            for (int indexColumn = worldMap.getOriginWorldMap().getX(); indexColumn < worldMap.getMaxLengthField(); indexColumn++) {
                Cell spawnLocation = new Cell(indexRow, indexColumn);
                Optional<? extends Entity> entity = worldMap.getEntity(spawnLocation);

                if (entity.isEmpty() && super.isPlaceEntity(worldMap.getSpawnRate(), probability)) {
                    if (hasConnectionWithAnyEntity(spawnLocation, previousSpawnLocations, worldMap)) {
                        worldMap.setEntity(spawnLocation, creature);
                        previousSpawnLocations.add(spawnLocation);
                    }
                }
            }
        }
    }

    public boolean hasConnectionWithAnyEntity(Cell spawnLocation, ArrayList<Cell> previousSpawnLocations, WorldMap worldMap) {
        for (Cell previousSpawnLocation : previousSpawnLocations) {
            Path path = new Path(worldMap, spawnLocation, previousSpawnLocation);
            if (path.isPathFound()) {
                return true;
            }
        }
        return previousSpawnLocations.isEmpty();
    }
}
