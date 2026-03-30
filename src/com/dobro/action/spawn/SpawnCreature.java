package com.dobro.action.spawn;

import com.dobro.Cell;
import com.dobro.worldmap.WorldMap;
import com.dobro.worldmap.WorldMapUtils;
import com.dobro.entity.Entity;
import com.dobro.path.PathFinder;

import java.util.List;
import java.util.function.Supplier;

public abstract class SpawnCreature extends Spawn {
    private final Class<? extends Entity> clazz;
    private final Supplier<Entity> supplier;

    public SpawnCreature(float probability, Class<? extends Entity> clazz, Supplier<Entity> supplier) {
        super(probability);
        this.clazz = clazz;
        this.supplier = supplier;
    }

    @Override
    protected void spawn(WorldMap worldMap, Cell spawnCell) {
        List<Cell> previousSpawnCells = WorldMapUtils.getCellsOfCertainType(clazz, worldMap);
        if (hasConnectionWithAnyEntity(spawnCell, previousSpawnCells, worldMap)) {
            worldMap.setEntity(spawnCell, supplier.get());
        }
    }

    private boolean hasConnectionWithAnyEntity(Cell spawnCell, List<Cell> previousSpawnCells, WorldMap worldMap) {
        for (Cell previousSpawnCell : previousSpawnCells) {
            PathFinder pathFinder = new PathFinder(worldMap, spawnCell, previousSpawnCell);
            if (pathFinder.isPathFound()) {
                return true;
            }
        }
        return previousSpawnCells.isEmpty();
    }
}
