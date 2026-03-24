package com.dobro.actions.spawn;

import com.dobro.Cell;
import com.dobro.WorldMap;
import com.dobro.WorldMapUtils;
import com.dobro.entity.Coin;
import com.dobro.entity.Entity;
import com.dobro.path.Path;

import java.util.List;

public class SpawnCoin extends Spawn {
    private final Class<? extends Entity> clazz;

    public SpawnCoin() {
        super(SpawnProbability.COIN.getProbability());
        this.clazz = SpawnDependency.COIN.getClazz();
    }

    @Override
    public void spawn(WorldMap worldMap, Cell spawnCell) {
        List<Cell> previousSpawnCells = WorldMapUtils.getCellsOfCertainType(clazz, worldMap);
        if (!hasConnectionWithPreviousSpawnCells(spawnCell, previousSpawnCells, worldMap)) {
            return;
        }
        worldMap.setEntity(spawnCell, new Coin());
    }

    private boolean hasConnectionWithPreviousSpawnCells(Cell spawnCell, List<Cell> previousSpawnCells, WorldMap worldMap) {
        for (Cell previousSpawnCell : previousSpawnCells) {
            Path path = new Path(worldMap, spawnCell, previousSpawnCell);
            if (!path.isPathFound()) {
                return false;
            }
        }
        return true;
    }
}