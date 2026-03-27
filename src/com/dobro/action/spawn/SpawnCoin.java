package com.dobro.action.spawn;

import com.dobro.Cell;
import com.dobro.worldmap.WorldMap;
import com.dobro.worldmap.WorldMapUtils;
import com.dobro.entity.Coin;
import com.dobro.entity.Entity;
import com.dobro.path.PathFinder;

import java.util.List;

public class SpawnCoin extends Spawn {
    private final Class<? extends Entity> clazz;

    public SpawnCoin() {
        super(SpawnProbability.COIN.getProbability());
        this.clazz = SpawnDependency.COIN.getClazz();
    }

    @Override
    protected void spawn(WorldMap worldMap, Cell spawnCell) {
        List<Cell> previousSpawnCells = WorldMapUtils.getCellsOfCertainType(clazz, worldMap);
        if (!hasConnectionWithPreviousSpawnCells(spawnCell, previousSpawnCells, worldMap)) {
            return;
        }
        worldMap.setEntity(spawnCell, new Coin());
    }

    private boolean hasConnectionWithPreviousSpawnCells(Cell spawnCell, List<Cell> previousSpawnCells, WorldMap worldMap) {
        for (Cell previousSpawnCell : previousSpawnCells) {
            PathFinder pathFinder = new PathFinder(worldMap, spawnCell, previousSpawnCell);
            if (!pathFinder.isPathFound()) {
                return false;
            }
        }
        return true;
    }
}