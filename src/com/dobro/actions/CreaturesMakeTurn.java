package com.dobro.actions;

import com.dobro.models.Creature;
import com.dobro.models.Entity;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.Map;

public class CreaturesMakeTurn extends Action {
    @Override
    public void execute(WorldMap worldMap) {
        Map<Cell, Entity> copyEntities = worldMap.getCopyEntities();
        for (Map.Entry<Cell, Entity> copyPieceOfWorldMap : copyEntities.entrySet()) {
            Cell location = copyPieceOfWorldMap.getKey();
            if (!hasContainInMainMap(worldMap, location)) {
                continue;
            }
            Entity entity = copyPieceOfWorldMap.getValue();
            if (Creature.class.isAssignableFrom(entity.getClass())) {
                Creature creature = (Creature) entity;
                creature.makeTurn(location, worldMap);
            }
        }
    }

    public boolean hasContainInMainMap(WorldMap worldMap, Cell location) {
        return worldMap.getEntities().containsKey(location);
    }
}
