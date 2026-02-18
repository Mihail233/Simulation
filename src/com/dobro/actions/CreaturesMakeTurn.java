package com.dobro.actions;

import com.dobro.models.Creature;
import com.dobro.models.Entity;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.Map;

public class CreaturesMakeTurn extends Action {
    @Override
    public void execute(WorldMap worldMap) {
        for (Map.Entry<Cell, Entity> pieceOfMap: worldMap.getEntities().entrySet()) {
            Entity entity = pieceOfMap.getValue();
            if (Creature.class.isAssignableFrom(entity.getClass())) {
                Creature creature = (Creature) entity;
                Cell location = pieceOfMap.getKey();
                creature.makeTurn(location, worldMap);
            }
        }
    }
}
