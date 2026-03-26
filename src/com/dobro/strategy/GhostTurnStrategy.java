package com.dobro.strategy;

import com.dobro.Cell;
import com.dobro.worldmap.WorldMap;
import com.dobro.actions.spawn.SpawnDependency;
import com.dobro.entity.Creature;

import java.util.Optional;

public class GhostTurnStrategy implements TurnStrategy {
    private final InteractionCellFinder InteractionCellFinder;

    public GhostTurnStrategy() {
        InteractionCellFinder = new InteractionCellFinder();
    }

    @Override
    public <T extends Creature> Optional<Cell> getInteractionCell(Cell currentCell, T entity, WorldMap worldMap) {
        return InteractionCellFinder.getInteractionCell(currentCell, entity, worldMap, SpawnDependency.GHOST.getClazz());
    }
}
