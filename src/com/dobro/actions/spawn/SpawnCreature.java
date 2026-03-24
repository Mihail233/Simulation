package com.dobro.actions.spawn;

import com.dobro.Cell;
import com.dobro.WorldMap;
import com.dobro.WorldMapUtils;
import com.dobro.entity.Entity;
import com.dobro.path.Path;

import java.util.List;
import java.util.function.Supplier;

public abstract class SpawnCreature extends Spawn {
    private final Class<? extends Entity> clazz;
    private final Supplier<Entity> supplier;

    public SpawnCreature(Class<? extends Entity> clazz, float probability, Supplier<Entity> supplier) {
        super(probability);
        this.clazz = clazz;
        this.supplier = supplier;
    }

    @Override
    public void spawn(WorldMap worldMap, Cell spawnCell) {
        List<Cell> previousSpawnCells = WorldMapUtils.getCellsOfCertainType(clazz, worldMap);
        if (hasConnectionWithAnyEntity(spawnCell, previousSpawnCells, worldMap)) {
            worldMap.setEntity(spawnCell, supplier.get());
        }
    }

    private boolean hasConnectionWithAnyEntity(Cell spawnCell, List<Cell> previousSpawnCells, WorldMap worldMap) {
        for (Cell previousSpawnCell : previousSpawnCells) {
            Path path = new Path(worldMap, spawnCell, previousSpawnCell);
            if (path.isPathFound()) {
                return true;
            }
        }
        return previousSpawnCells.isEmpty();
    }
}
