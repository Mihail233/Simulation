package com.dobro.actions;

import com.dobro.Cell;
import com.dobro.worldmap.WorldMap;
import com.dobro.entity.Creature;
import com.dobro.entity.Entity;

import java.util.Map;
import java.util.Optional;

public class CreaturesMakeTurn extends Action {
    @Override
    public void execute(WorldMap worldMap) {
        Map<Cell, Entity> entities = worldMap.getEntities();
        for (Map.Entry<Cell, Entity> pieceOfMap : entities.entrySet()) {

            Cell cell = pieceOfMap.getKey();
            Optional<Entity> entity = worldMap.getEntity(cell);
            if (entity.isPresent()) {
                if (entity.get() instanceof Creature creature) {
                    creature.makeTurn(cell, worldMap);
                }
            }
        }
    }
}
